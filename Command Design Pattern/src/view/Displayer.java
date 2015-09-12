package view;

public interface Displayer<T> {

	void getDisplayer(Drawable<T> draw);
	void display();
}
