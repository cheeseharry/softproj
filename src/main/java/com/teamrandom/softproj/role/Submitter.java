package com.teamrandom.softproj.role;

import com.teamrandom.softproj.component.Artifact;
import com.teamrandom.softproj.component.Review;

import java.util.ArrayList;
import java.util.List;


public class Submitter extends Role {
	
	private List<Artifact> artifactsSubmitted;
	

	public Submitter() {
		artifactsSubmitted = new ArrayList<Artifact>();
	}
	
	public void submitArtifact(Artifact artifact) {
		artifactsSubmitted.add(artifact);
		throw new UnsupportedOperationException();
	}
	
	public void viewArtifact(Artifact artifact) {
		throw new UnsupportedOperationException();
	}
	
	public void viewComment(Review review) {
		throw new UnsupportedOperationException();
	}
	
}
