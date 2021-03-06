/**
 * The MIT License
 * Copyright © 2019 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtls.fairmetadata4j.parser;

import nl.dtls.fairmetadata4j.model.Identifier;
import nl.dtls.fairmetadata4j.util.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for IdentifierParser.
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @version 0.1
 * @since 2016-11-30
 */
public class IdentifierParserTest {

    private final List<Statement> STMTS = ExampleFilesUtils.getFileContentAsStatements(
            ExampleFilesUtils.FDP_METADATA_FILE, ExampleFilesUtils.FDP_URI.toString());

    /**
     * Test null id uri, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullIDUri() throws Exception {
        System.out.println("Test : Parse null id uri");
        IRI identifierURI = null;
        IdentifierParser.parse(STMTS, identifierURI);
        fail("This test is execpeted to throw an error");
    }

    /**
     * Test null rdf statements, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullStatements() throws Exception {
        System.out.println("Test : Parse null statements");
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        IdentifierParser.parse(null, identifierURI);
        fail("This test is execpeted to throw an error");
    }

    /**
     * Test empty rdf statements, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyStatements() throws Exception {
        System.out.println("Test : Parse empty statements");
        List<Statement> statements = new ArrayList();
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }

    /**
     * Test valid metadata id.
     */
    @Test
    public void testValidMetadataID() {
        System.out.println("Parse fdp metadata ID");
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        Identifier result = IdentifierParser.parse(STMTS, identifierURI);
        assertNotNull(result);
    }

    /**
     * Test valid repo id.
     */
    @Test
    public void testValidRepoID() {
        System.out.println("Parse fdp repo ID");
        IRI identifierURI = ExampleFilesUtils.FDP_REPO_ID_URI;
        Identifier result = IdentifierParser.parse(STMTS, identifierURI);
        assertNotNull(result);
    }

}
