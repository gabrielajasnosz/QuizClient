package com.pdfgenerator.guiPrymitywne;

import com.pdfgenerator.model.NetworkRequests;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guiPrymitywne {
    private JButton button1;
    private JPanel panel1;

    public guiPrymitywne() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NetworkRequests.getByGET(2);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {

       /* przyklad uzycia HTTP CLIENTA dla zadania typu POST
        System.out.println("Test metody POST: \n");
        exampleHttpClientForPostMethod();
        System.out.println();*/

        // przyklad uzycia HTTP CLIENTA dla zadania typu GET
        JFrame guiPrymitywne = new JFrame("GUI");
        guiPrymitywne.setContentPane(new guiPrymitywne().panel1);
        guiPrymitywne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiPrymitywne.pack();
        guiPrymitywne.setVisible(true);
    }
}