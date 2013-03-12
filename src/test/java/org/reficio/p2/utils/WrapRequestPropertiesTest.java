/**
 * Copyright (c) 2012 Reficio (TM) - Reestablish your software! All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.reficio.p2.utils;

import org.junit.Test;
import org.mockito.Mockito;
import org.reficio.p2.P2Artifact;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author: Tom Bujok (tom.bujok@gmail.com)
 * <p/>
 * Reficio™ - Reestablish your software!
 * www.reficio.org
 */
public class WrapRequestPropertiesTest {

    @Test(expected = RuntimeException.class)
    public void testIOExceptionTranslation() {
        ResolvedArtifact resolvedArtifact = mock(ResolvedArtifact.class, Mockito.RETURNS_DEEP_STUBS);
        P2Artifact p2Artifact = mock(P2Artifact.class, Mockito.RETURNS_DEEP_STUBS);
        WrapRequestProperties wrp = new WrapRequestProperties(resolvedArtifact, p2Artifact);
        wrp.calculateNames();
    }

    @Test
    public void testCalculateSourceName() {
        ResolvedArtifact resolvedArtifact = mock(ResolvedArtifact.class, Mockito.RETURNS_DEEP_STUBS);
        P2Artifact p2Artifact = mock(P2Artifact.class, Mockito.RETURNS_DEEP_STUBS);
        WrapRequestProperties wrp = new WrapRequestProperties(resolvedArtifact, p2Artifact);
        assertEquals("org.reficio.p2.source", wrp.calculateSourceName(null, "org.reficio.p2"));
        assertEquals("org.reficio.p2.source", wrp.calculateSourceName(null, "org.reficio.p2; singleton=true"));
        assertEquals("org.reficio.P2.source", wrp.calculateSourceName(null, "org.reficio.P2"));
        assertEquals("Reficio P2 Source", wrp.calculateSourceName("Reficio P2", "org.reficio.p2"));
        assertEquals("reficio p2 source", wrp.calculateSourceName("reficio p2", "org.reficio.p2"));
        assertEquals("org.reficio.P2.Source", wrp.calculateSourceName("org.reficio.P2", "org.reficio.p2"));
        assertEquals("org.reficio.p2.source", wrp.calculateSourceName("org.reficio.p2", "org.reficio.p2"));
        assertEquals("org.reficio.p2.source", wrp.calculateSourceName("org.reficio.p2", "org.reficio.p2; singleton=true"));
    }


}
