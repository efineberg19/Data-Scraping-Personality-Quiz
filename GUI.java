//basic utilities and graphics
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.ArrayList;

//data scraping
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.restfb.Parameter;

//opening URL
import java.net.URL;
import java.awt.Desktop;
import java.net.*;
import java.io.IOException;

/**
 * Runs the personality quiz and scrapes the data from Facebook.
 *
 * @author Beth Fineberg
 * @version 1.0
 */
public class GUI extends JFrame implements ActionListener
{
    String personality = "";
    
    //I got these questions from here: https://www.lusd.org/cms/lib6/CA01001399/Centricity/Domain/711/THE%20MYERS-BRIGGS.pdf
    QuizQuestions q1 = new QuizQuestions(false, "\nA) expend energy, enjoy groups or B) conserve energy, enjoy one-on-one", "EI");
    QuizQuestions q2 = new QuizQuestions(false, "\nA) interpret literally or B) look for meaning and possibilities", "SN");
    QuizQuestions q3 = new QuizQuestions(false, "\nA) logical, thinking, questioning or B) empathetic, feeling, accommodating", "TF");
    QuizQuestions q4 = new QuizQuestions(false, "\nA) organized, orderly or B) flexible, adaptable", "JP");
    QuizQuestions q5 = new QuizQuestions(false, "\nA) more outgoing, think out loud or B) more reserved, think to yourself", "EI");
    QuizQuestions q6 = new QuizQuestions(false, "\nA) practical, realistic, experiential or B) imaginative, innovative, theoretical", "SN");
    QuizQuestions q7 = new QuizQuestions(false, "\nA) candid, straight forward, frank or B) tactful, kind, encouraging", "TF");
    QuizQuestions q8 = new QuizQuestions(false, "\nA) plan, schedule or B) unplanned, spontaneous", "JP");
    QuizQuestions q9 = new QuizQuestions(false, "\nA)  external, communicative, express yourself or B)  internal, reticent, keep to yourself", "EI");
    QuizQuestions q10 = new QuizQuestions(false, "\nA) standard, usual, conventional or B) different, novel, unique", "SN");
    QuizQuestions q11 = new QuizQuestions(false, "\nA) firm, tend to criticize, hold the line or B)  gentle, tend to appreciate, conciliate", "TF");
    QuizQuestions q12 = new QuizQuestions(false, "\nA) regulated, structured or B) easygoing, “live” and “let live”", "JP");

    QuizQuestions[] questions = {q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12};

    //the number question the user is on
    int questionCount = 0;
    
    JPanel instructionsPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel upperCenter = new JPanel();
    JPanel lowerCenter = new JPanel();
    JPanel buttonPanel = new JPanel();

    JTextArea report;
    JTextArea instructions = new JTextArea("Click the letter below that " 
        + "corresponds with the statement that \n   you think most accurately " 
        + "describes you, the click Submit.");    

    JButton start, a, b, quit, reveal, replay, yourPersonality;
    
    Color blue = new Color(93, 215, 255);
    Color gray = new Color(76, 76, 76);

    Font smallFont = new Font("Comic Sans MS", Font.BOLD, 14);
    Font mediumFont = new Font("Comic Sans MS", Font.BOLD, 18);

    /**
     * GUI Constructor
     * 
     * Creates button and sets us framework for drawings.
     */
    public GUI(String title)
    {
        super(title);

        //creates the instructions at the top of the GUI
        instructions.setBackground(blue);
        instructions.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        instructions.setEditable(false);
        instructions.setFont(mediumFont);
        instructions.setForeground(Color.WHITE);
        instructions.setVisible(false);

        //creates the area for questions to be displayed
        report = new JTextArea("\nWelcome! Press Start to Begin");
        report.setBackground(new Color (248, 248, 248));
        report.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        report.setEditable(false);
        report.setFont(smallFont);
        report.setForeground(gray);

        //creates start button to begin quiz
        start = new JButton("Start");
        start.setActionCommand("start");
        start.addActionListener(this);
        start.setFont(smallFont);
        start.setForeground(gray);

        //creates quit button to exit app
        quit = new JButton("Quit");
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        quit.setFont(smallFont);
        quit.setForeground(gray);

        //creates restart button for after user finishes quiz
        replay = new JButton("Try Again");
        replay.setActionCommand("replay");
        replay.addActionListener(this);
        replay.setFont(smallFont);
        replay.setVisible(false); //not visible at start of game
        replay.setForeground(gray);

        //creates button that opens website with info about your personality
        yourPersonality = new JButton("Learn More About Your Personality");
        yourPersonality.setActionCommand("yourPersonality");
        yourPersonality.addActionListener(this);
        yourPersonality.setFont(smallFont);
        yourPersonality.setVisible(false); //not visible at start of game
        yourPersonality.setForeground(gray);

        //creates A button for user to respond to quiz
        a = new JButton("A");
        a.setActionCommand("a");
        a.addActionListener(this);
        a.setVisible(false); //not visible at start of game
        a.setFont(smallFont);
        a.setForeground(gray);
        
        //creates B button for user to respond to quiz
        b = new JButton("B");
        b.setActionCommand("b");
        b.addActionListener(this);
        b.setVisible(false); //not visible at start of game
        b.setFont(smallFont);
        b.setForeground(gray);

        //creates button to show quiz results
        reveal = new JButton("Reveal Your Profile!");
        reveal.setActionCommand("reveal");
        reveal.addActionListener(this);
        reveal.setVisible(false); //not visible at start of game
        reveal.setFont(smallFont);
        reveal.setForeground(gray);

        //adds buttons and text to appropriate panels
        centerPanel.add(report);
        buttonPanel.add(start);
        buttonPanel.add(a);
        buttonPanel.add(b);
        buttonPanel.add(reveal);
        buttonPanel.add(yourPersonality);
        buttonPanel.add(replay);
        buttonPanel.add(quit);
        instructionsPanel.add(instructions);

        //adds panels to GUI
        this.add(instructionsPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        //final color details
        instructionsPanel.setBackground(blue);
        buttonPanel.setBackground(blue);
        centerPanel.setBackground(new Color (248, 248, 248));

        this.setSize(700, 250);
        setVisible(true);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    /**
     * Start Timer
     *
     * Creates a Timer object and starts timer
     */
    public void startTimer()
    {
        Timer t = new Timer(1, this); //1 is timer delay
        t.setActionCommand("timerFired");
        t.start();
    }

    /**
     * Action Performed
     * 
     * Responds to a click of a button.
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getActionCommand().equals("start"))
        {
            //makes appropriate buttons visible
            start.setVisible(false);
            a.setVisible(true);
            b.setVisible(true);
            instructions.setVisible(true);

            //shows first question
            report.setText(q1.getQuestion());
        }
        else if (e.getActionCommand().equals("a"))
        {
            if(questionCount < questions.length - 1)
            {
                //save the answer chosen
                questions[questionCount].setResponse(true);
                
                questionCount++;
                
                //shows next question
                report.setText(questions[questionCount].getQuestion());
            }
            else if(questionCount == questions.length - 1)
            {
                //if at last question, make reveal button only option
                a.setVisible(false);
                b.setVisible(false);
                reveal.setVisible(true);
            }
        }
        else if (e.getActionCommand().equals("b"))
        {
            if(questionCount < questions.length - 1)
            {
                //save the answer chosen
                questions[questionCount].setResponse(false);
                
                questionCount++;
                
                //shows next question
                report.setText(questions[questionCount].getQuestion());
            }
            else if(questionCount == questions.length - 1)
            {
                //if at last question, make reveal button only option
                a.setVisible(false);
                b.setVisible(false);
                reveal.setVisible(true);
            }
        }
        else if (e.getActionCommand().equals("reveal"))
        {
            //all possible traits for personality
            int intro = 0;
            int extro = 0;
            int sense = 0;
            int intu = 0;
            int think = 0;
            int feel = 0;
            int judge = 0;
            int perc = 0;

            for(int i = 0; i < questions.length; i++)
            {
                //all possible traits that can occur if A is chosen
                if(questions[i].getResponse())
                {
                    //adds to trait counter depending on type of question
                    if(questions[i].getComponent().equals("E"))
                    {
                        extro++;
                    }
                    else if(questions[i].getComponent().equals("S"))
                    {
                        sense++;
                    }
                    else if(questions[i].getComponent().equals("T"))
                    {
                        think++;
                    }
                    else if(questions[i].getComponent().equals("J"))
                    {
                        judge++;
                    }
                }
                //all possible traits that can occur if B is chosen
                else
                {
                    //adds to trait counter depending on type of question
                    if(questions[i].getComponent().equals("I"))
                    {
                        intro++;
                    }
                    else if(questions[i].getComponent().equals("N"))
                    {
                        intu++;
                    }
                    else if(questions[i].getComponent().equals("F"))
                    {
                        feel++;
                    }
                    else if(questions[i].getComponent().equals("P"))
                    {
                        perc++;
                    }
                }
            }

            //figures out dominant trait based on the counter
            if(intro > extro)
            {
                personality += "I";
            }
            else
            {
                personality += "E";
            }

            if(sense > intu)
            {
                personality += "S";
            }
            else
            {
                personality += "N";
            }

            if(think > feel)
            {
                personality += "T";
            }
            else
            {
                personality += "F";
            }

            if(judge > perc)
            {
                personality += "J";
            }
            else
            {
                personality += "P";
            }

            //deals with the visibility of buttons
            replay.setVisible(true);
            yourPersonality.setVisible(true);
            reveal.setVisible(false);
            
            /*
             * This value is associated with my account. I tried to get this
             * through the imbedded browser login, but it wouldn't work. I can
             * only use the Facebook functionality for my account. I found my
             * access token on through the tools offered on the Facebook 
             * developers website. This token allows for access to certain
             * personal data through scopes that I have specified.
             */
            String accessToken = "EAAd3STjUliMBAH80jFHn6qN92mJ7JA7MqXNKjwS681x6lsTXj9atyQhQrlPg9xtqNnEl5HsxbQZAV4VxwBTZBDsc3p5pILRNnJBFeQxC78ygEE6WvDQJETdYC0p27ZAFsgnBjVgYqzHxF0PZAc4FwWMd6hFGdhoZD";

            FacebookClient fbClient = new DefaultFacebookClient(accessToken);

            //has access to the scopes specified
            User user = fbClient.fetchObject("me", User.class, Parameter.with("fields", "id,name,birthday,hometown,location,tagged_places,likes,gender,photos,email"));

            /*
             * Honestly, this took a very really long time to figure out.
             * I used the restfb packed to do the queries and make the data 
             * usable. Resstfb allows me to do more with the data, but I thought
             * that this was the coolest of the functions. If you want, I can
             * talk to you and go into more depth about how this all works.
             * 
             * I used these tutorial series for help (and Stack Overflow):
             * https://www.youtube.com/watch?v=hnWzcRhsueU
             * https://www.youtube.com/watch?v=P9z1dRPmeUQ
             */
            report.setText("\nYour Personality: " + personality + "\nYour Birthday: " + user.getBirthday() 
                + "\nYour Hometown: " + user.getHometownName() + "\nYour Location: " + user.getLocation().getName());
        }
        else if (e.getActionCommand().equals("quit"))
        {
            //exits the program
            System.exit(0);
        }
        else if (e.getActionCommand().equals("replay"))
        {
            //resets the buttons and allows user to restart
            questionCount = 0;
            personality = "";
            report.setText("\nWelcome! Press Start to Begin");
            start.setVisible(true);
            replay.setVisible(false);
            yourPersonality.setVisible(false);
        }
        else if (e.getActionCommand().equals("yourPersonality"))
        {
            //opens URL with information about the personality based on the quiz
            Desktop desktop = Desktop.getDesktop();

            try
            {
                URI myNewLocation = new URI("https://www.16personalities.com/" + personality.toLowerCase() + "-personality");
                desktop.browse(myNewLocation);
            }
            catch (IOException ioe) 
            {
            }
            catch(URISyntaxException urie)
            {
            }
        }
    }
}
