/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata.model.Identifier;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * Unit tests for IdentifierParser.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class IdentifierParserTest {
    
    /**
     * Test null id uri, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullIDUri() throws Exception {
        System.out.println("Test : Parse null id uri");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI identifierURI = null;
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null rdf statements, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullStatements() throws Exception {
        System.out.println("Test : Parse null statements");
        List<Statement> statements = null;
        URI identifierURI = new URIImpl(ExampleFilesUtils.FDP_METADATA_ID_URI);
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }
    
     /**
     * Test empty rdf statements, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyStatements() throws Exception {
        System.out.println("Test : Parse empty statements");
        List<Statement> statements = new ArrayList();
        URI identifierURI = new URIImpl(ExampleFilesUtils.FDP_METADATA_ID_URI);
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }
    
    
    /**
     * Test valid metadata id.
     */
    @Test
    public void testValidMetadataID() {
        System.out.println("Parse fdp metadata ID");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI identifierURI = new URIImpl(ExampleFilesUtils.FDP_METADATA_ID_URI);
        Identifier result = IdentifierParser.parse(statements, identifierURI);
        assertNotNull(result);
    }
    
    /**
     * Test valid repo id.
     */
    @Test
    public void testValidRepoID() {
        System.out.println("Parse fdp repo ID");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI identifierURI = new URIImpl(ExampleFilesUtils.FDP_REPO_ID_URI);
        Identifier result = IdentifierParser.parse(statements, identifierURI);
        assertNotNull(result);
    }
    
}
