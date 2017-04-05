package main.core;

/**
 * Interface that the BlockBuilder class implements so that other objects
 * can control the BlockBuilder.
 * @author Group 6
 *
 */
public interface BlockBuilderController {
	
	/**
	 * Refresh the text area of the block builder that shows the current
	 * scenario buffer text.
	 */
	public void refreshBuffer();

}
