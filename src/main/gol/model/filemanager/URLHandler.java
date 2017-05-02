package main.gol.model.filemanager;

import main.gol.controller.util.Dialogs;

import java.net.URL;

/**
 * URLHandler class handles the selection of the correct URL type.
 * <p>
 * It make sure that plaintext and RLE content is handled accordingly.
 *
 * @author Frode Kristian Mathiassen
 * @author Tommy Pedersen
 * @author Magnus Kjernsli Hansen-Mollerud
 * @version 2.0
 */
public class URLHandler {

    private final Dialogs dialog = new Dialogs();
    private String theUrlType;

    /**
     * selectUrlType gets the URL as a string and sets the correspondent URL as a new value.
     * <p>
     * This value is split at each dot, and the last index contains the type.
     * theUrlType is updated with the correct type value, and if it is RLE, the RLEDecoder is instantiated.
     *
     * @param inURL String
     * @throws Exception e
     */
    public void selectUrlType(String inURL) throws Exception {

        URL url = new URL(inURL);

        try {
            String[] fileType = url.getFile().split("[.]");
            int itemCount = fileType.length;
            if (fileType[itemCount - 1].contains("txt") || fileType[itemCount - 1].contains("cells")) {
                theUrlType = "Text Url";
            } else if (fileType[itemCount - 1].contains("rle")) {
                theUrlType = "RLE Url";
                Decoder RLE = new Decoder();
                RLE.RLEDecodeURL(inURL);
            }
        } catch (Exception e) {
            dialog.urlError();
            System.err.println("Error: " + e);
        }
    }

    /**
     * Getter for the URL type.
     *
     * @return String theUrlType
     */
    public String getUrlType() {
        return this.theUrlType;
    }
}