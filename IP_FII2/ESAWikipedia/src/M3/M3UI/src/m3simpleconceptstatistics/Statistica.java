package m3simpleconceptstatistics;

import java.io.File;

/**
 *
 * @author Ovidiu
 */
public interface Statistica {
    public abstract void analizeaza(File f);
	public String getResult();
	public String getResultRest();
}
