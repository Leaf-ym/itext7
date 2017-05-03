/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Joris Schellekens on 5/2/2017.
 */
@Category(UnitTest.class)
public class PdfAttachedFileTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/kernel/utils/PdfAttachedFileTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/kernel/utils/PdfAttachedFileTest/";

    @Test
    public void extractSingleAttachmentFromPdf()
    {
        String filename = sourceFolder + "FileWithSingleAttachment.pdf";
        String attachmentFilename = sourceFolder + "FileWithSingleAttachment_001.jpg";

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(filename));

            // create
            PdfAttachedFile rootFile = new PdfAttachedFile(pdfDocument);

            // assert number of attachments
            assert(rootFile.listFiles().length == 1);

            // check content of attachments
            PdfAttachedFile attachment = (PdfAttachedFile) rootFile.listFiles()[0];
            byte[] bytes0 = attachment.getBytes();
            byte[] bytes1 = Files.readAllBytes(new File(attachmentFilename).toPath());

            assert(bytes0.length == bytes1.length);
            for(int i=0;i<bytes0.length;i++)
                assert(bytes0[i] == bytes1[i]);

        } catch (IOException e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void extractMultipleAttachmentFromPdf()
    {
        String filename = sourceFolder + "FileWithMultipleAttachments.pdf";
        String[] attachmentFilename = { sourceFolder + "FileWithMultipleAttachments_001.jpg",
                                        sourceFolder + "FileWithMultipleAttachments_002.jpg",
                                        sourceFolder + "FileWithMultipleAttachments_003.jpg",
                                        sourceFolder + "FileWithMultipleAttachments_004.jpg"
                                            };

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(filename));

            // create
            PdfAttachedFile rootFile = new PdfAttachedFile(pdfDocument);

            // assert number of attachments
            assert(rootFile.listFiles().length == attachmentFilename.length);

            // check content of attachments
            for(int i=0;i<rootFile.listFiles().length;i++) {

                PdfAttachedFile attachment = (PdfAttachedFile) rootFile.listFiles()[i];
                byte[] bytes0 = attachment.getBytes();
                byte[] bytes1 = Files.readAllBytes(new File(attachmentFilename[i]).toPath());

                // compare bytes
                assert (bytes0.length == bytes1.length);
                for (int j = 0; j < bytes0.length; j++)
                    assert (bytes0[j] == bytes1[j]);

            }
        } catch (IOException e) {
            e.printStackTrace();
            assert(false);
        }
    }


}
