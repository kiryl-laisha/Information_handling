package org.laisha.information_handling.reader;

import org.laisha.information_handling.exception.ProjectException;

public interface ReaderFromFile {

    String readTextFromFile(String filePath) throws ProjectException;
}
