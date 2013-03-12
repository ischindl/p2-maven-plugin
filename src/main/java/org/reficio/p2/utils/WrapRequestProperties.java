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

import aQute.lib.osgi.Analyzer;
import aQute.lib.osgi.Jar;
import org.reficio.p2.P2Artifact;

import java.io.IOException;

public class WrapRequestProperties {

	private String name;
	private String symbolicName;
	private String version;

	private String sourceName;
	private String sourceSymbolicName;
	private String sourceVersion;

	private BundleUtils bundleUtils = new BundleUtils();
	private ResolvedArtifact resolvedArtifact;
	private P2Artifact p2artifact;

	public WrapRequestProperties(ResolvedArtifact resolvedArtifact, P2Artifact p2artifact) {
		this.resolvedArtifact = resolvedArtifact;
		this.p2artifact = p2artifact;
	}

	public void calculateNames() {
		try {
			this.symbolicName = calculateSymbolicName();
			this.name = calculateName(symbolicName);
			this.version = calculateVersion();

			this.sourceSymbolicName = calculateSourceSymbolicName(symbolicName);
			this.sourceName = calculateSourceName(name, symbolicName);
			this.sourceVersion = calculateSourceVersion(version);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private String calculateName(String symbolicName) {
		return calculateSingleton(symbolicName);
	}

	private String calculateSingleton(String text) {
		int poz;
		if ((poz = text.indexOf(";")) > 0) {
			return text.substring(0, poz);
		}
		return text;
	}

	private String calculateSymbolicName() throws IOException {
		String symbolicName = null;
		if (resolvedArtifact.isRoot()) {
			Object symbolicNameValue = p2artifact.getInstructions().get(Analyzer.BUNDLE_SYMBOLICNAME);
			symbolicName = symbolicNameValue != null ? symbolicNameValue.toString() : null;
		}
		if (symbolicName == null) {
			symbolicName = bundleUtils.getBundleSymbolicName(new Jar(resolvedArtifact.getArtifact().getFile()));
		}
		if (symbolicName == null) {
			symbolicName = bundleUtils.calculateBundleSymbolicName(resolvedArtifact.getArtifact());
		}
		return symbolicName;
	}

	private String calculateVersion() throws IOException {
		String version = null;
		if (resolvedArtifact.isRoot()) {
			Object versionValue = p2artifact.getInstructions().get(Analyzer.BUNDLE_VERSION);
			version = versionValue != null ? versionValue.toString() : null;
		}
		if (version == null) {
			version = bundleUtils.getBundleVersion(new Jar(resolvedArtifact.getArtifact().getFile()));
		}
		if (version == null) {
			version = bundleUtils.calculateBundleVersion(resolvedArtifact.getArtifact());
		}
		return version;
	}

	private String calculateSourceVersion(String version) {
		return version;
	}

	public String calculateSourceSymbolicName(String symbolicName) {

		return calculateSingleton(symbolicName) + ".source";
	}

	public String calculateSourceName(String name, String symbolicName) {
		String sourceName = null;
		if (name == null) {
			sourceName = calculateSingleton(symbolicName) + ".source";
		} else {
			sourceName = name.trim();
			if (sourceName.matches(".*\\s+.*")) {
				sourceName += " ";
			} else {
				sourceName += ".";
			}
			if (sourceName.matches(".*[A-Z].*")) {
				sourceName += "Source";
			} else {
				sourceName += "source";
			}
		}
		return sourceName;
	}

	public String getName() {
		return name;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public String getVersion() {
		return version;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceSymbolicName() {
		return sourceSymbolicName;
	}

	public String getSourceVersion() {
		return sourceVersion;
	}
}
