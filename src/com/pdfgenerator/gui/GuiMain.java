package com.pdfgenerator.gui;

import com.pdfgenerator.model.AnswerData;
import com.pdfgenerator.model.NetworkRequests;
import com.pdfgenerator.model.QuestionData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GuiMain extends javax.swing.JFrame {
    private JTextField questionTextArea;
    public JPanel panel1;
    public JButton answer1Button;
    private JButton answer4Button;
    private JButton answer3Button;
    private JButton answer2Button;
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

    AnswerData dataAnswer= new AnswerData();
    QuestionData zbiorPytan;
    String yourAnswer = "";
    int answersToCheckCount = 4;
    int id=1;

    public void disableAnswerButtons() {
        answer1Button.setEnabled(false);
        answer2Button.setEnabled(false);
        answer3Button.setEnabled(false);
        answer4Button.setEnabled(false);
    }

    public void enableAnswerButtons() {
        answer1Button.setEnabled(true);
        answer2Button.setEnabled(true);
        answer3Button.setEnabled(true);
        answer4Button.setEnabled(true);
    }

    public void setNextQuestionTexts() {
        try {
            zbiorPytan = NetworkRequests.getByGET(id);
            questionTextArea.setText(zbiorPytan.getQuestion());
            answer1Button.setText(zbiorPytan.getAnswers()[0]);
            answer2Button.setText(zbiorPytan.getAnswers()[1]);
            answer3Button.setText(zbiorPytan.getAnswers()[2]);
            answer4Button.setText(zbiorPytan.getAnswers()[3]);
            questionPriceTextField.setText((zbiorPytan.getPoints()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void answerButtonClickAction(int answerNumber) {
        yourAnswer = yourAnswer + answerNumber;
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
        answer1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(1);
                answer1Button.setEnabled(false);
            }
        });

        answer2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(2);
                answer2Button.setEnabled(false);
            }
        });

        answer3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(3);
                answer3Button.setEnabled(false);
            }
        });

        answer4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerButtonClickAction(4);
                answer4Button.setEnabled(false);
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:

             //  dataanswer.setSelectedAnswers(yourAnswer.);
              //  System.out.print(dataanswer.getSelectedAnswers());

                System.out.println(yourAnswer);

              if (zbiorPytan.isLastQuestion()==false) {
                  dataAnswer.setQuestionId(id);
                  dataAnswer.setLastQuestion(zbiorPytan.isLastQuestion());
                  dataAnswer.setSelectedAnswers(yourAnswer);
                  try {
                      NetworkRequests.answerData(dataAnswer);
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
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
                yourAnswer="";
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
                answer1Button.setVisible(true);
                answer2Button.setVisible(true);
                answer3Button.setVisible(true);
                answer4Button.setVisible(true);
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