package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HexCalculate extends Calculate 
{
	private String problem;

	public HexCalculate(String problem) 
	{
		super(problem);
		// TODO 自动生成的构造函数存根
		this.problem = problem;
	}

	
	public  static Double hexStringToAlgorithm(String hex)
    {
        //16转Double
        int max = hex.length();
        int result = 0;

        for (int i = max; i > 0; i--)
        {
            char c = hex.charAt(i - 1);
            int algorism = 0;

            if (c >= '0' && c <= '9')
            {   algorism = c - '0'; }
            else
            {   algorism = c - 55; }

            result += Math.pow(16, max - i) * algorism;
        }

        Double res;
        res = Double.valueOf(result);

        return res;
    }

	@Override
    public List<Object> getOperand(String s)
    {
        List<Object> list = new ArrayList<>();
        int begin = 0;

        for (int i = 0; i < s.length(); i++)                                    //补全省略的乘号
        {
            char c = s.charAt(i);
            if(c=='('&&!String.valueOf(s.charAt(i-1)).matches("\\+|-|\\*|/|\\(|\\)"))
            {
                StringBuffer buffer = new StringBuffer(s);
                buffer.insert(i,'*');
                s = buffer.toString();
            }
        }

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (String.valueOf(c).matches("\\+|-|\\*|/|\\(|\\)"))
            {                                                                   //遇符号则停
                String sub = s.substring(begin, i);                             //数 归入list
                if (!sub.equals(""))
                {
                    list.add(hexStringToAlgorithm(sub));
                }

                if( !String.valueOf(c).matches("\\(|\\)") )
                {                                                              //运算符归入list
                    list.add(parseOperation(String.valueOf(c)));
                }
                else
                {                                                             //括号归入list
                    list.add(String.valueOf(c));
                }
                begin = i + 1;
            }
        }
        String sub = s.substring(begin);
        if (!sub.equals(""))
        {
            list.add(hexStringToAlgorithm(sub));
        }
        return list;
    }
	
	public static void main(String[] args) 
	{
		// TODO 自动生成的方法存根
		try
        {
			
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			String Strcontent = in.nextLine();
            HexCalculate cal = new HexCalculate(Strcontent);
            double res = cal.getResult();
            System.out.println(String.valueOf(res));
        }
        catch (Exception e)
        {
            System.out.println("Error!");
        }
	}

}
