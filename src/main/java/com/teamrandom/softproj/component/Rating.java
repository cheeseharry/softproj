package com.teamrandom.softproj.component;


import com.teamrandom.softproj.businessObject.User;

public class Rating {
	
	private float score;
	private Artifact artifact;
	private User reviewer_who_rated;
	private User lecturer_who_rated;
	
	public Rating() {
		throw new UnsupportedOperationException();
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public Artifact getArtifact() {
		return artifact;
	}
	
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	
	public User getReviewer_who_rated() {
		return reviewer_who_rated;
	}
	
	public void setReviewer_who_rated(User reviewer_who_rated) {
		this.reviewer_who_rated = reviewer_who_rated;
	}
	
	public User getLecturer_who_rated() {
		return lecturer_who_rated;
	}
	
	public void setLecturer_who_rated(User lecturer_who_rated) {
		this.lecturer_who_rated = lecturer_who_rated;
	}
	
	
}
