import { Injectable } from '@angular/core';
import { Hero } from './hero';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable';
import { MessageService } from './message.service';

import { Http, Headers } from '@angular/http';
import { tap, catchError, map } from 'rxjs/operators';

const httpOptions = {
  headers: new Headers({ 'Content-Type': 'application/json' })
}

@Injectable()
export class HeroService {
  private heroesUrl = 'api/heroes';

  constructor(private http: Http, private messageService: MessageService) { }

  getHeroes(): Observable<Hero[]> {
    // return this.http.get<Hero[]>(this.heroesUrl) // import the HttpClientModule symbol from @angular/common/http
    return this.http.get(this.heroesUrl)
      .map(res => res.json().data)
      .pipe(
        tap(heroes => this.log(`fetched heroes`)),
        catchError(this.handleError('getHeroes', []))
      );
  }

  getHeroNo404<Data>(id: number): Observable<Hero> {
    const url = `${this.heroesUrl}/?id=${id}`;
    return this.http.get(url)
      .map(res => res.json().data)
      .pipe(
        map(heroes => heroes[0]),
        tap(h => { const outcome = h ? `fetched` : `did not find`; this.log(`${outcome} hero id=${id}`) }),
        catchError(this.handleError(`getHero id=${id}`))
      )
  }

  getHero(id: number): Observable<Hero> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.get(url)
      .map(res => res.json().data)
      .pipe(
        tap(_ => this.log(`fetched hero id=${id}`)),
        catchError(this.handleError<Hero>(`getHero id=${id}`))
      );
  }

  updateHero(hero: Hero): Observable<any> {
    const url = `${this.heroesUrl}/${hero.id}`;
    return this.http.put(url, hero, httpOptions).pipe(
      tap(_ => this.log(`updated hero id=${hero.id}`)),
      catchError(this.handleError<any>('updateHero'))
    );
  }

  addHero(hero: Hero): Observable<Hero> {
    return this.http.post(this.heroesUrl, hero, httpOptions)
      .map(res => res.json().data)
      .pipe(
        tap((hero: Hero) => this.log(`added hero w/ id=${hero.id}`)),
        catchError(this.handleError<Hero>('addHero'))
      );
  }

  deleteHero(hero: Hero | number): Observable<Hero> {
    const id = typeof hero === 'number' ? hero : hero.id;
    const url = `${this.heroesUrl}/${id}`;

    return this.http.delete(url, httpOptions)
      .map(res => res.json().data)
      .pipe(
        tap(_ => this.log(`deleted id=${id}`)),
        catchError(this.handleError<Hero>('deleteHero'))
      );
  }

  searchHeroes(term: string): Observable<Hero[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.http.get(`api/heroes/?name=${term}`)
      .map(res => res.json().data)
      .pipe(
        tap(_ => this.log(`found heroes matching "${term}"`)),
        catchError(this.handleError<Hero[]>('searchHeroes', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add("HeroService: " + message);
  }
}
