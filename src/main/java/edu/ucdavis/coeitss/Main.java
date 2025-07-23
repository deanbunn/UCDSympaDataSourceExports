package edu.ucdavis.coeitss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Initiate IAM Worker
        UCDIAMWorker uiamWrkr = new UCDIAMWorker();

        //Initiate Sympa Major Export Settings
        List<SympaMajorExport> lSympaMajorExports = new ArrayList<>();
        lSympaMajorExports.add(new SympaMajorExport("GECE", "CivilEnvironGradStudents.txt"));
        //lSympaMajorExports.add(new SympaMajorExport("GBIM", "BiomedicalGradStudents.txt"));
        //lSympaMajorExports.add(new SympaMajorExport("GCSI", "ComputerScienceGradStudents.txt"));

        try
        {
            
            for(SympaMajorExport sme : lSympaMajorExports)
            {

                HashSet<String> hstUsrEmails = uiamWrkr.Get_Campus_Addresses_for_Students_In_MajorCode(sme.MajorCode);

                for(String usrEmail : hstUsrEmails)
                {
                    System.out.println(usrEmail);
                }
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        
    }
}