/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata.model.FDPMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.R3D;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-07
 * @version 0.1
 */
public class FDPMetadataParser extends MetadataParser<FDPMetadata> {
    
    @Override
    protected FDPMetadata createMetadata() {
        return new FDPMetadata();
    }
    
    @Override
    public FDPMetadata parse(List<Statement> statements, IRI fdpURI) {
        FDPMetadata metadata = super.parse(statements, fdpURI);
        List<IRI> catalogs = new ArrayList();
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(fdpURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    metadata.setHomepage((IRI) object);
                } else if (predicate.equals(RDFS.SEEALSO)) {
                    metadata.setSwaggerDoc((IRI) object);
                } else if (predicate.equals(R3D.DATA_CATALOG)) {
                    catalogs.add((IRI) object);
                } else if (predicate.equals(R3D.REPO_IDENTIFIER)) {
                    metadata.setRepostoryIdentifier(IdentifierParser.parse(
                            statements, (IRI)object));
                } else if (predicate.equals(R3D.INSTITUTION_COUNTRY)) {
                    metadata.setInstitutionCountry((IRI) object);
                } else if (predicate.equals(R3D.REPO_START_DATE)) {
                    metadata.setStartDate((f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.REPO_LAST_UPDATE)) {
                    metadata.setLastUpdate((f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.INSTITUTION)) {
                    metadata.setInstitution(AgentParser.parse(
                            statements, (IRI)object));
                }
            }
        }
        if(!catalogs.isEmpty()) {
            metadata.setCatalogs(catalogs);
        }
        return metadata;
    }
}
