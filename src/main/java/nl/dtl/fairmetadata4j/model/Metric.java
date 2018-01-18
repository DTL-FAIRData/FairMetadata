/**
 * The MIT License
 * Copyright © 2017 DTL
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
package nl.dtl.fairmetadata4j.model;

import org.eclipse.rdf4j.model.IRI;

/**
 * Metric object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2018-01-16
 * @version 0.1
 */
public class Metric {

    /**
     * @return the uri
     */
    public IRI getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(IRI uri) {
        this.uri = uri;
    }

    /**
     * @return the value
     */
    public IRI getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(IRI value) {
        this.value = value;
    }

    /**
     * @return the metricType
     */
    public IRI getMetricType() {
        return metricType;
    }

    /**
     * @param metricType the metricType to set
     */
    public void setMetricType(IRI metricType) {
        this.metricType = metricType;
    }
    
    private IRI uri;
    private IRI value;
    private IRI metricType;
    
}
