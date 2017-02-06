/**
 * The MIT License
 * Copyright © 2016 DTL
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
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.model.DataRecordMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

/**
 * Parser for datarecord metadata
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-25
 * @version 0.1
 */
public class DataRecordMetadataParser extends 
        MetadataParser< DataRecordMetadata> {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DataRecordMetadataParser.class);

    @Override
    protected DataRecordMetadata createMetadata() {
        return new DataRecordMetadata();
    }
    
    /**
     * Parse RDF statements to datarecord metadata object
     * @param statements        List of RDF statement list
     * @param dataRecordURI     Datarecord URI
     * @return                  DataRecordMetadata object 
     */
    @Override
    public DataRecordMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull IRI dataRecordURI)  {
        Preconditions.checkNotNull(dataRecordURI, 
                "Datarecord URI must not be null.");
        Preconditions.checkNotNull(statements, 
                "Datarecord statements must not be null.");
        LOGGER.info("Parsing distribution metadata");
        DataRecordMetadata metadata = super.parse(statements, 
                dataRecordURI);
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(dataRecordURI)) {
                if (predicate.equals(FDP.RML_MAPPING)) {
                    metadata.setRmlURI((IRI) object);
                } else if (predicate.equals(FDP.REFERS_TO)) {
                    metadata.setDistributionURI((IRI) object);
                } else if (predicate.equals(DCTERMS.ISSUED)) {
                    metadata.setDataRecordIssued(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.MODIFIED)) {
                    metadata.setDataRecordModified(f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to datarecord metadata object
     * 
     * @param dataRecordMetadata  Datarecord metadata as a RDF string
     * @param dataRecordID        Datarecord ID
     * @param dataRecordURI       Datarecord URI
     * @param datasetURI            Dataset URI
     * @param format                RDF string's RDF format
     * @return                      DataRecordMetadata object 
     * @throws nl.dtl.fairmetadata.io.MetadataParserException 
     */
    public DataRecordMetadata parse (@Nonnull String dataRecordMetadata, 
            @Nonnull String dataRecordID, @Nonnull IRI dataRecordURI, 
            IRI datasetURI, @Nonnull RDFFormat format) 
            throws MetadataParserException {
        Preconditions.checkNotNull(dataRecordMetadata, 
                "Datarecord metadata string must not be null."); 
        Preconditions.checkNotNull(dataRecordID, 
                "Datarecord ID must not be null.");
        Preconditions.checkNotNull(dataRecordURI, 
                "Datarecord URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        
        Preconditions.checkArgument(!dataRecordMetadata.isEmpty(), 
                "The datarecord metadata content can't be EMPTY");
        Preconditions.checkArgument(!dataRecordID.isEmpty(), 
                "The datarecord id content can't be EMPTY");        
        try {
            Model modelDistribution = Rio.parse(new StringReader(dataRecordMetadata), 
                    dataRecordURI.stringValue(), 
                    format);
            Iterator<Statement> it = modelDistribution.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            
            DataRecordMetadata metadata = this.parse(statements, 
                    dataRecordURI);
//            metadata.setIdentifier(new LiteralImpl(dataRecordID, 
//                    XMLSchema.STRING));
            metadata.setParentURI(datasetURI);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading datarecord metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing datarecord metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        }        
    }
    
    /**
     * Parse RDF string to dataset dataRecordMetadata object
     *
     * @param dataRecordMetadata Datarecord metadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return DataRecordMetadata object
     * @throws MetadataParserException
     */
    public DataRecordMetadata parse(@Nonnull String dataRecordMetadata,
            IRI baseURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(dataRecordMetadata,
                "Datarecord metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!dataRecordMetadata.isEmpty(),
                "The datarecord metadata content can't be EMPTY");
        try {
            Model modelCatalog;
            if (baseURI != null) {
                modelCatalog = Rio.parse(new StringReader(dataRecordMetadata),
                        baseURI.stringValue(), format);
            } else {
                String dummyURI = "http://example.com/dummyResource";
                modelCatalog = Rio.parse(new StringReader(
                        dataRecordMetadata), dummyURI, format);
            }
            Iterator<Statement> it = modelCatalog.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            IRI catalogURI = (IRI) statements.get(0).getSubject();
            DataRecordMetadata metadata = this.parse(statements, catalogURI);
            metadata.setUri(null);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading datarecord metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing datarecord metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        }
    }
    
}