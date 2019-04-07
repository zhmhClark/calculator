package calculator;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

enum OperationValue 
{
	plus("+"), minus("-"), multiply("*"), divide("/");

	String value;

	OperationValue(String value) 
	{	this.value = value;	}

	@Override
	public String toString() 
	{	return this.value;	}

}

class Operation 
{
	OperationValue value;
	int priority;

	final static int low = 1;
	final static int high = 2;

	public Operation(OperationValue value, int priority) 
	{
		this.value = value;
		this.priority = priority;
	}

	@Override
	public String toString() 
	{	return this.value.toString();	}
}

public class Calculate 
{
	static Operation plus, minus, multiply, divide, left, right;

	static 
	{
		plus = new Operation(OperationValue.plus, Operation.low);
		minus = new Operation(OperationValue.minus, Operation.low);
		multiply = new Operation(OperationValue.multiply, Operation.high);
		divide = new Operation(OperationValue.divide, Operation.high);
	}

	private String problem;

    public Calculate(String problem)
    {	this.problem = problem;	}
	
	public List<Object> getOperand(String s) 
	{
		List<Object> list = new ArrayList<>();
		int begin = 0;
		for (int i = 0; i < s.length(); i++)
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
			if ( String.valueOf(c).matches("\\+|-|\\*|/|\\(|\\)") ) 
			{
				String sub = s.substring(begin, i);
				if (!sub.equals("")) 
				{
					list.add(Double.parseDouble(sub));
				}
				
				if( !String.valueOf(c).matches("\\(|\\)") )
				{
					list.add(parseOperation(String.valueOf(c)));
				}
				else
				{
					list.add(String.valueOf(c));
				}
				begin = i + 1;
			}
		}
		
		String sub = s.substring(begin);
		
		if (!sub.equals("")) 
		{
			list.add(Double.parseDouble(sub));
		}
		
		return list;
	}

	public Operation parseOperation(String c) 
	{
		if (c.equals(OperationValue.plus.toString())) 
			{	return plus;	}
		if (c.equals(OperationValue.minus.toString())) 
			{	return minus;	}
		if (c.equals(OperationValue.multiply.toString())) 	
			{	return multiply;}
		if (c.equals(OperationValue.divide.toString()))
			{	return divide;	}
		
		return null;
	}

	public Stack<Object> parseToReversePolishNotation(String s) 
	{
		s = s.replace(" ", "");
		s = s.replace('÷','/');
        s = s.replace('×','*');
		Stack<String> operationStack = new Stack<>();
		Stack<Object> expressionStack = new Stack<>();
		List<Object> operandList = getOperand(s);
		
		for (Object o : operandList) 
		{
			if (o instanceof Double) 
			{
				expressionStack.push((Double) o);
			} 
			
			else if (o instanceof String) 
			{
				String si = (String) o;
				if (si.equals("(")) 
				{
					operationStack.push(si);
				} 
				else if (si.equals(")")) 
				{
					while (!operationStack.peek().equals("(")) 
					{
						expressionStack.push(parseOperation(operationStack.pop()));
					}
					operationStack.pop();
				}
			} 
			
			else if (o instanceof Operation) 
			{
				Operation operation = (Operation) o;
				
				while (!operationStack.isEmpty() && !operationStack.peek().equals("(")&& operation.priority <= parseOperation(operationStack.peek()).priority) 
				{	expressionStack.push(parseOperation(operationStack.pop()));	}
				
				operationStack.push(operation.value.toString());
			}
		}
		
		while (!operationStack.isEmpty()) 
		{	expressionStack.push(parseOperation(operationStack.pop()));}
		
		return expressionStack;
	}
	
	public double calculateReversePolishNotation(Stack<Object> expressionStack) 
	{
		Stack<Object> calculateStack = new Stack<>();
		
		for (int i = 0; i < expressionStack.size(); i++) 
		{
			Object o = expressionStack.get(i);
			if (o instanceof Double) 
			{
				calculateStack.push(o);
			} 
			else if (o instanceof Operation) 
			{
				double number1 = (Double) calculateStack.pop();
				double number2 = (Double) calculateStack.pop();
				calculateStack.push(calculateTwoNumbers(number1, number2, (Operation) o));
			}
		}
		
		return (Double) calculateStack.pop();
	}

	public Double calculateTwoNumbers(double number1, double number2, Operation operation) 
	{
		if (operation.value == OperationValue.plus) 
		{	return number2 + number1;	}
		if (operation.value == OperationValue.minus) 
		{	return number2 - number1;	}
		if (operation.value == OperationValue.multiply) 
		{	return number2 * number1;	}
		if (operation.value == OperationValue.divide) 
		{	return number2 / number1;	}
		return null;
	}

	public double getResult()
    {	return calculateReversePolishNotation(parseToReversePolishNotation(problem));	}
	
	public static void main(String[] args) 
	{
		// TODO 自动生成的方法存根
		try
        {
			
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			String Strcontent = in.nextLine();
            Calculate cal = new Calculate(Strcontent);
            double res = cal.getResult();
            System.out.println(String.valueOf(res));
            System.out.println(Double.toHexString(res));
        }
        catch (Exception e)
        {
            System.out.println("Error!");
        }
	}

}
