/**
 * 
 * @author winse
 * 
 */
public class ModelSwitch<T> {

	public enum Gender {
		MAN("man"), WOMAN("woman"), NULL("");

		String mark;

		Gender(String gender) {
			this.mark = gender;
		}

		public String getMark() {
			return mark;
		}

		/*
		 * 增加NULL，实现对异常情况处理的统一
		 */
		public static Gender getByName(String name) {
			for (Gender element : Gender.values()) {
				if (element.mark.equalsIgnoreCase(name)) {
					return element;
				}
			}
			return NULL;
		}
	}

	/**
	 * @param gender
	 *            "man"/"woman"
	 */
	public T doSwitch(String gender) {
		switch (Gender.getByName(gender)) {
		case MAN:
			return caseManModel();
		case WOMAN:
			return caseWomanModel();
		default:
			return defaultCase();
		}
	}

	public T caseManModel() {
		return null;
	}

	public T caseWomanModel() {
		return null;
	}

	public T defaultCase() {
		return null;
	}

} // ModelSwitch