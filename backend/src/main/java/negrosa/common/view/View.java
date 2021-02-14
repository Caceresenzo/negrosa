package negrosa.common.view;

public interface View {
	
	public static interface Public {
	}
	
	public static interface Detailed extends Public {
	}
	
	public static interface Private extends Detailed {
	}
	
	public static interface Internal extends Private {
	}
	
}