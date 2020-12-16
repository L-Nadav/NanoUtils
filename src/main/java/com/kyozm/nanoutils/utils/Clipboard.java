package com.kyozm.nanoutils.utils;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Clipboard {

    public static void writeImageToClipboard(Image image) {
        if (image == null)
            return;
        TransferableImage transferable = new TransferableImage( image );
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
    }


    static class TransferableImage implements Transferable {

        private Image img;

        public TransferableImage(Image img) {
            this.img = img;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] {DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
            return dataFlavor == DataFlavor.imageFlavor;
        }

        @Override
        public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
            if (isDataFlavorSupported(dataFlavor)) {
                return img;
            }
            else {
                throw new UnsupportedFlavorException(dataFlavor);
            }
        }
    }
}
