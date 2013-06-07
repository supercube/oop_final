package cards;

import java.awt.Image;

public interface DImage {
	public abstract Image getImage(int img_id);
	public abstract int getImageWidth();
	public abstract int getImageHeight();
}
