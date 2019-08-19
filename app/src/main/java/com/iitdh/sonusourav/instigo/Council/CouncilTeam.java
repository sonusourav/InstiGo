package com.iitdh.sonusourav.instigo.Council;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CouncilTeam implements Serializable {

  private String _id;
  private String teamName;
  private String teamDesc;
  private String teamSize;
  private String teamUrl;
  private ArrayList<CouncilUserClass> team;

  public CouncilTeam(){};

  public CouncilTeam(String id,String teamName,String teamDesc,String teamSize,String teamUrl,ArrayList<CouncilUserClass>  team){
    this._id=id;
    this.teamUrl=teamUrl;
    this.teamName=teamName;
    this.teamDesc=teamDesc;
    this.teamSize=teamSize;
    this.team=team;
  }

  public String getId() {
    return _id;
  }

  public void setId(String id) {
    this._id = id;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getTeamDesc() {
    return teamDesc;
  }

  public void setTeamDesc(String teamDesc) {
    this.teamDesc = teamDesc;
  }

  public String getTeamSize() {
    return teamSize;
  }

  public void setTeamSize(String teamSize) {
    this.teamSize = teamSize;
  }

  public ArrayList<CouncilUserClass>  getTeam() {
    return team;
  }

  public void setTeam(ArrayList<CouncilUserClass>  team) {
    this.team = team;
  }

  public String getTeamUrl() {
    return teamUrl;
  }

  public void setTeamUrl(String teamUrl) {
    this.teamUrl = teamUrl;
  }
}
