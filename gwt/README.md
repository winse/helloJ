## ����˵��

```
mvn package
```

�������������󣬰���Ŀ���뵽eclipse��

����`gwt.launch`���ᵯ��gwt swing���ڡ����**Launch GWT Module**��**Launch Default Browser**��������м���<http://127.0.0.1:8888/Hello.html?gwt.codesvr=127.0.0.1:9997>��

������򿪺����û��GWT Plugin����ת�������װҳ�棨[��ǽ](http://www.uudaili.org/index.html)�ɣ���������ֱ�Ӹ��ݲ���İ汾���ض�Ӧ���������

* [gwt-dev-plugin-1.26-rc1.xpi](https://code.google.com/p/google-web-toolkit/downloads/list)
* [Firefox Setup 26.0](ftp://ftp.mozilla.org/pub/mozilla.org/firefox/releases/26.0/win32/en-US/Firefox%20Setup%2026.0.exe)

��װ���Ժ���firefox���������ոյĵ�ַ���Ϳ��������Ч���ˡ�

![](images/ok.png)

## ���ֿ�

* ��ǽ
* chrome�Ĳ�����ܰ�װ���޷����а�װ��⵽�������⣺ ��Ӧ��Ҫ��ʹ�� NPAPI ���)
* firefoxҳ��İ�װ��ť��������
* ͨ��<http://google-web-toolkit.googlecode.com>ҳ������ذ汾firefox�汾���ԣ�

## gwt

* �޸�webAppCreator������cygwin

```
#!/bin/sh
HOMEDIR=`dirname $0`;

# �����ύ��windows��java����ǰִ��
function Cygwin_Patch_PathConvert() {

	cygwin=false
	case "`uname`" in
	CYGWIN*) cygwin=true;;
	esac

	# cygwin path translation
	if $cygwin; then
		CLASSPATH=`cygpath -p -w "$CLASSPATH"`
	fi
}

CLASSPATH=$HOMEDIR/gwt-user.jar:$HOMEDIR/gwt-dev.jar
Cygwin_Patch_PathConvert

java -cp $CLASSPATH com.google.gwt.user.tools.WebAppCreator "$@";
```

* ����helloworld

```
winse@Lenovo-PC /cygdrive/f/workspaces/gwt
$ webAppCreator -overwrite -maven com.github.winse.Hello
```

��ô��������У������Ķ�README.txt

