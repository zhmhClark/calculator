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
		// TODO �Զ����ɵĹ��캯�����
		this.problem = problem;
	}

	
	public  static Double hexStringToAlgorithm(String hex)
    {
        //16תDouble
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

        for (int i = 0; i < s.length(); i++)                                    //��ȫʡ�Եĳ˺�
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
            {                                                                   //��������ͣ
                String sub = s.substring(begin, i);                             //�� ����list
                if (!sub.equals(""))
                {
                    list.add(hexStringToAlgorithm(sub));
                }

                if( !String.valueOf(c).matches("\\(|\\)") )
                {                                                              //���������list
                    list.add(parseOperation(String.valueOf(c)));
                }
                else
                {                                                             //���Ź���list
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
		// TODO �Զ����ɵķ������
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
