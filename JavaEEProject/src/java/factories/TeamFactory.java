/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.ITeamRepo;
import sqlAccess.SqlTeam;

/**
 *
 * @author Nikunj
 */
public class TeamFactory {
    public static ITeamRepo makeTeamRepo() {
        return new SqlTeam();
    }  
}

