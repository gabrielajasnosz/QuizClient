package com.pdfgenerator.gui;

import com.pdfgenerator.model.AnswerData;
import com.pdfgenerator.model.NetworkRequests;
import com.pdfgenerator.model.QuestionData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class GuiMain extends javax.swing.JFrame {
    private JTextField questionTextArea;
    public JPanel panel1;
    public JButton answerAButton;
    private JButton answerDButton;
    private JButton answerCButton;
    private JButton answerBButton;
    private JButton nextQuestionButton;
    private JButton endQuizButton;
    private JButton startGameButton;
    private JLabel aLabel;
    private JLabel bLabel;
    private JLabel cLabel;
    private JLabel dLabel;
    private JLabel questionPriceLabel;
    private JLabel gameOverLabel;
    private JTextField questionPriceTextField;

    QuestionData zbiorPytan;
    Integer[] yourAnswer ={0,0,0,0};
    int selectedAnswerIndex=0;
    int answersToCheckCount = 4;
    int id=1;

    public void disableAnswerButtons() {
        answerAButton.setEnabled(false);
        answerBButton.setEnabled(false);
        answerCButton.setEnabled(false);
        answerDButton.setEnabled(false);
    }

    public void enableAnswerButtons() {
        answerAButton.setEnabled(true);
        answerBButton.setEnabled(true);
        answerCButton.setEnabled(true);
        answerDButton.setEnabled(true);
    }

    public void setNextQuestionTexts() {
        try {
            zbiorPytan = NetworkRequests.getByGET(id);
            questionTextArea.setText(zbiorPytan.getQuestion());
            answerAButton.setText(zbiorPytan.getAnswers()[0]);
            answerBButton.setText(zbiorPytan.getAnswers()[1]);
            answerCButton.setText(zbiorPytan.getAnswers()[2]);
            answerDButton.setText(zbiorPytan.getAnswers()[3]);
            questionPriceTextField.setText((zbiorPytan.getPoints()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void answerButtonClickAction(int answerNumber) {
        yourAnswer[selectedAnswerIndex]=answerNumber;
        selectedAnswerIndex++;
        if (answersToCheckCount > 1) {
            --answersToCheckCount;
            nextQuestionButton.setEnabled(true);
        } else if (answersToCheckCount == 1) {
            --answersToCheckCount;
            disableAnswerButtons();
            nextQuestionButton.setEnabled(true);
        }else if(answersToCheckCount == 3){
            nextQuestionButton.setEnabled(true);
        } else {
            System.out.println("Negative answersToCheckCount!!!");
        }
       /* //!! sprawdzanie odpowiedzi na biezaco
        CheckAnswer checkAnswer = new CheckAnswer();
        float countUsersGoodAnswersForCurrentQuestion = checkAnswer.checkAnswer(yourAnswer, zbiorPytan.get(indeks).getCorrectAnswers());
        if (countUsersGoodAnswersForCurrentQuestion != 0) {
            isAnswerGood.setText("Tak, dobrze!" + countUsersGoodAnswersForCurrentQuestion * 100 + "%");
            wynik = wynik + ((Float.parseFloat(zbiorPytan.get(indeks).getPoints()) / zbiorPytan.get(indeks).getCorrectAnswers().replaceAll(",", "").length()));
            scoreTextField.setText(" " + wynik);
        } else {
            isAnswerGood.setText("Niestety nie :(");
        }*/
    }

    public GuiMain()throws Exception {
        answerAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(1);
                answerAButton.setEnabled(false);
            }
        });

        answerBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(2);
                answerBButton.setEnabled(false);
            }
        });

        answerCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(3);
                answerCButton.setEnabled(false);
            }
        });

        answerDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(4);
                answerDButton.setEnabled(false);
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(yourAnswer[0]);
                System.out.print(yourAnswer[1]);
                System.out.print(yourAnswer[2]);
                System.out.print(yourAnswer[3]);
                selectedAnswerIndex=0;
                for(int i = 0; i<yourAnswer.length;i++){
                    yourAnswer[i]=0;
                }
              if (zbiorPytan.isLastQuestion()==false) {
                  AnswerData myAnswers = new AnswerData();
                  myAnswers.setQuestionId(id);
                  myAnswers.setLastQuestion(zbiorPytan.isLastQuestion());
                 //TODO myAnswers.setSelectedAnswersAnswers();


                  id++;
                  setNextQuestionTexts();
                  enableAnswerButtons();
                  nextQuestionButton.setEnabled(false);
                  answersToCheckCount=4;
                } else {
                    nextQuestionButton.setEnabled(false);
                    gameOverLabel.setVisible(true);
                    gameOverLabel.setText("KONIEC");
                    disableAnswerButtons();
                }
            }
        });

        endQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcja = JOptionPane.showConfirmDialog(rootPane, "Czy napewno chcesz zakonczyc gre ?", "Uwaga!", JOptionPane.YES_NO_OPTION);
                if (opcja == 0) {
                    panel1.setVisible(false);
                }
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNextQuestionTexts();
                questionTextArea.setVisible(true);
                startGameButton.setVisible(false);
                nextQuestionButton.setVisible(true);
                nextQuestionButton.setEnabled(false);
                answerAButton.setVisible(true);
                answerBButton.setVisible(true);
                answerCButton.setVisible(true);
                answerDButton.setVisible(true);
                aLabel.setVisible(true);
                bLabel.setVisible(true);
                cLabel.setVisible(true);
                dLabel.setVisible(true);
                questionPriceLabel.setVisible(true);
                endQuizButton.setVisible(true);
                questionPriceTextField.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        JFrame GuiMain = new JFrame("GUI");
        GuiMain.setContentPane(new GuiMain().panel1);
        GuiMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GuiMain.pack();
        GuiMain.setVisible(true);
    }
}