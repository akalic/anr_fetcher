import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by jgonzal2 on 10/15/2014.
 */
public class Test
{
    public static void main(String[] args)
    {
        BufferedReader reader = null;
        URL url = null;
        String output = "";

        try
        {
            url = new URL("http://www.netrunnerdb.com/api/cards/");
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            output = reader.readLine();
            System.out.println(output);
            JSONArray json = new JSONArray(output);
            System.out.println(json.length());
            JSONObject j = (JSONObject)json.get(0);
            System.out.println(j);
            System.out.println(j.getString("code"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(reader != null)
                    reader.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
