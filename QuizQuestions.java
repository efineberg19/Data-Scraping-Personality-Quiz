
/**
 * Creates an easy interface for storing and accessing questions and the
 * user's responses.
 *
 * @author Beth Fineberg
 * @version 1.0
 */
public class QuizQuestions
{
    //user's response
    boolean answer;
    //content of the question
    String question;
    //type of personality traits determined by question
    String type;
    
    /**
     * Constructor for objects of class QuizQuestions
     */
    public QuizQuestions(boolean a, String q, String t)
    {
        answer = a;
        question = q;
        type = t;
    }

    /**
     * Get Question
     * Returns the text of a question.
     *
     * @return String the content of the question
     */
    public String getQuestion()
    {
        return question;
    }
    
    /**
     * Get Response
     * Returns true if user picks a and false if user picks b.
     *
     * @return boolean user's response
     */
    public boolean getResponse()
    {
        return answer;
    }
    
    /**
     * Set Response
     * Stores the user's response. Stores true if user picks a and false if 
     * user picks b.
     *
     * @param boolean user's response
     */
    public void setResponse(boolean a)
    {
        answer = a;
    }
    
    /**
     * Get Component
     * Gets the personality component that corresponds with the user's answer
     * based on the type of question.
     *
     * @return String the personality component
     */
    public String getComponent()
    {
        if(type.equals("EI"))
        {
            if(answer)
            {
                return "E";
            }
            else
            {
                return "I";
            }
        }
        else if(type.equals("SN"))
        {
            if(answer)
            {
                return "S";
            }
            else
            {
                return "N";
            }
        }
        else if(type.equals("TF"))
        {
            if(answer)
            {
                return "T";
            }
            else
            {
                return "F";
            }
        }
        else if(type.equals("JP"))
        {
            if(answer)
            {
                return "J";
            }
            else
            {
                return "P";
            }
        }
        else
        {
            return "error";
        }
    }
}
