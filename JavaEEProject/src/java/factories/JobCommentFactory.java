/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.IJobCommentRepo;
import sqlAccess.SqlJobComment;

/**
 *
 * @author Alena Selezneva
 */
public class JobCommentFactory {
    public static IJobCommentRepo makeJobCommentRepo() {
        return new SqlJobComment();
    } 
}
