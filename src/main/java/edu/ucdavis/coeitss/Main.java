package edu.ucdavis.coeitss;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Initiate IAM Worker
        UCDIAMWorker uiamWrkr = new UCDIAMWorker();

        //Export Folder Location
        String exportFolderLoc = "/home/dbunn/UCDTesting/";

        //Initiate Sympa Major Export Settings
        List<SympaMajorExport> lSympaMajorExports = new ArrayList<>();
        lSympaMajorExports.add(new SympaMajorExport("GECE", "CivilEnvironGradStudents.txt"));
        lSympaMajorExports.add(new SympaMajorExport("GBIM", "BiomedicalGradStudents.txt"));
        lSympaMajorExports.add(new SympaMajorExport("GCSI", "ComputerScienceGradStudents.txt"));
        lSympaMajorExports.add(new SympaMajorExport("GMAE", "MechanicalAeroGradStudents.txt"));
        lSympaMajorExports.add(new SympaMajorExport("GEEC", "ElecticalGradStudents.txt"));

        try
        {
            
            for(SympaMajorExport sme : lSympaMajorExports)
            {

                //Pull Unique List of Student Email Addresses Based Upon Major Code
                HashSet<String> hstUsrEmails = uiamWrkr.Get_Campus_Addresses_for_Students_In_MajorCode(sme.MajorCode);

                //Initiate Writer for Export File
                try(BufferedWriter bfWriter = new BufferedWriter(new FileWriter(exportFolderLoc + sme.ExportFileName)))
                {

                    //Write Out Each Returned Email Address to Export File
                    for(String usrEmail : hstUsrEmails)
                    {
                        bfWriter.write(usrEmail);
                        bfWriter.newLine();
                    }

                    //Close Out Writer
                    bfWriter.close();

                    //Display Progress
                    System.out.println("Exported major " + sme.MajorCode + " to " + sme.ExportFileName);

                }
                catch(Exception eio)
                {
                    System.out.println(eio.toString());
                }

            }//End of lSympaMajorExports For
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

    }
}