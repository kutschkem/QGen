package com.github.kutschkem.Qgen.ui;

import java.io.IOException;

import org.apache.uima.UIMAException;

import com.github.kutschkem.Qgen.QuestionExtractor;

public class Main {

    /**
     * @param args
     * @throws IOException
     * @throws UIMAException
     */
    public static void main(String[] args) throws UIMAException, IOException {
        new QuestionExtractor().extract(args[0]);

    }

}
