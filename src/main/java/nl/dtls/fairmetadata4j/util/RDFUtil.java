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
package nl.dtls.fairmetadata4j.util;

import org.eclipse.rdf4j.model.*;

import java.util.List;
import java.util.stream.Collectors;

import static nl.dtls.fairmetadata4j.util.ValueFactoryHelper.i;

public class RDFUtil {

    /****************************************************
     *** Subject
     ****************************************************/
    public static List<Resource> getSubjectsBy(Model m, IRI predicate, Resource object) {
        return m.filter(null, predicate, object)
                .stream()
                .map(Statement::getSubject)
                .collect(Collectors.toList());
    }

    public static Resource getSubjectBy(Model m, IRI predicate, Resource object) {
        List<Resource> subjects = getSubjectsBy(m, predicate, object);
        return subjects.size() > 0 ? subjects.get(0) : null;
    }

    /****************************************************
     *** Objects
     ****************************************************/
    public static List<Value> getObjectsBy(Model m, Resource subject, IRI predicate) {
        return m.filter(subject, predicate, null)
                .stream()
                .map(Statement::getObject)
                .collect(Collectors.toList());
    }

    public static List<Value> getObjectsBy(Model m, String subject, String predicate) {
        return getObjectsBy(m, i(subject, m), i(predicate, m));
    }

    public static Value getObjectBy(Model m, Resource subject, IRI predicate) {
        List<Value> objects = getObjectsBy(m, subject, predicate);
        return objects.size() > 0 ? objects.get(0) : null;
    }

    public static String getStringObjectBy(Model m, Resource subject, IRI predicate) {
        Value object = getObjectBy(m, subject, predicate);
        return object != null ? object.stringValue() : null;
    }

    public static boolean containsObject(Model m, String subject, String predicate) {
        return getObjectsBy(m, subject, predicate).size() > 0;
    }

    /****************************************************
     *** Update
     ****************************************************/
    public static void update(Model m, Resource subj, IRI pred, Value obj, Resource... contexts) {
        m.remove(subj, pred, null, contexts);
        if (subj != null && pred != null && obj != null) {
            m.add(subj, pred, obj, contexts);
        }
    }

    public static <T extends Value> void update(Model m, Resource subj, IRI pred, List<T> list, Resource... contexts) {
        m.remove(subj, pred, null, contexts);
        list.forEach(obj -> {
            m.add(subj, pred, obj, contexts);
        });
    }

    /****************************************************
     *** Other
     ****************************************************/
    public static void checkNotLiteral(Value val) {
        if (val instanceof Literal) {
            throw new IllegalArgumentException("Objects of accessRights statements expected to be IRI");
        }
    }

    public static IRI removeLastPartOfIRI(IRI uri) {
        String uriWithoutLastPart = uri.getNamespace();
        return i(uriWithoutLastPart.substring(0, uriWithoutLastPart.length() - 1));
    }

}
