import java.util.*;
import java.io.*;
public class Myset<X>
{
	public node<X> head;
	public node<X> tail;
	public int mysetsize;
	public boolean IsEmpty()
	{
		return head == null;
	}
	public boolean IsMember(X element)
	{
		if (head == null)
			return false;
		else
		{
			node<X> a = head;
			while(a!= null)
			{
				if(a.getdata().equals(element))
					return true;
				a = a.getnext();
			}
			return false;
		}
	}
	public void addElement(X element)
	{
		if(!this.IsMember(element))
			{
				if(head==null)
				{
					node<X> c = new node<X>();
					c.setdata(element);
					head = tail = c;mysetsize++;
					return;
				}
				node<X> c = new node<X>();
				c.setdata(element);
				tail.setnext(c);
				c.setprev(tail);
				tail = c;
				mysetsize++;
			}  
	}
	public void delete(X element)
	{
		try
		{
			if(head == null)
				throw new Exception();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			node<X> a = head;
		while(a!= null)
			{
				if(a.getdata().equals(element))
					{
						if((a.getprev()==null)||(a.getnext()==null))
						{if(a.getprev()==null)
						{
							if(a.getnext()!=null)
							{a.getnext().setprev(null);
							this.head = a.getnext();}
							else 
							{
								this.head = null;
								this.tail = null;
							}
						}
						if(a.getnext()==null)
						{
							if(a.getprev()!=null)
							{a.getprev().setnext(null);
							this.tail = a.getprev();}
							else 
								{this.head = null;
									this.tail = null;}
						}
						mysetsize--;
						return;}

						a.getprev().setnext(a.getnext());
						a.getnext().setprev(a.getprev());
						mysetsize--;
						return ;
					}
				a = a.getnext();
			}
			throw new Exception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public Myset<X> Union(Myset<X> a)
	{
		Myset<X> b = new Myset<X>();
		
		
		
			node<X> c = new node<X>();
			node<X> thisnode = this.head;
			node<X> anode = a.head;
			while (anode!= null)
			{
				if(b.head == null)
				{
					c.setdata(anode.getdata());
					b.head = c;
					anode = anode.getnext();
					b.mysetsize++;
				}
				else
				{
					node<X> d = new node<X>();
					d.setdata(anode.getdata());
					d.setprev(c);
					c.setnext(d);
					c = c.getnext();
					anode = anode.getnext();
					b.mysetsize++;
				}
			}

			while(thisnode!= null)
			{
				if(b.head == null)
				{
					c.setdata(thisnode.getdata());
					b.head = c;
					b.mysetsize++;
					
				}
				else if((b.head!=null)&&(!a.IsMember(thisnode.getdata())))
				{
					node<X> d = new node<X>();
					d.setdata(thisnode.getdata());
					d.setprev(c);
					c.setnext(d);
					c = c.getnext();
					b.mysetsize++;
					
				}
				thisnode = thisnode.getnext();
			}
			b.tail = c;
			return b;
		
	}
	public Myset<X> Intersection(Myset<X> a)
	{
		Myset<X> b = new Myset<X>();
		node<X> c = new node<X>();
		node<X> thisnode = this.head;
			node<X> anode = a.head;
			while(thisnode!= null)
			{
				if(a.IsMember(thisnode.getdata()))
				{
					if(b.head == null)
				{
					c.setdata(thisnode.getdata());
					b.head = c;
					b.mysetsize++;
					
				}
				else 
				{
					node<X> d = new node<X>();
					d.setdata(thisnode.getdata());
					d.setprev(c);
					c.setnext(d);
					c = c.getnext();
					b.mysetsize++;
					
				}
				}
				thisnode = thisnode.getnext();
			}
			b.tail = c;
			return b;


	}
	/*public static void main(String[] args) {
		try{String content = new Scanner(new FileInputStream("stack_cprogramming")).useDelimiter("\\Z").next();

		String j = "a'";
		char k = j.charAt(1);
		content = content.replace(',',' ');
		content = content.replace('{',' ');
		content = content.replace('}',' ');
		content = content.replace('[',' ');
		content = content.replace(']',' ');
		content = content.replace('<',' ');
		content = content.replace('>',' ');
		content = content.replace('=',' ');
		content = content.replace('(',' ');
		content = content.replace(')',' ');
		content = content.replace('.',' ');
		content = content.replace(';',' ');
		content = content.replace('"',' ');
		content = content.replace('?',' ');
		content = content.replace('#',' ');
		content = content.replace('!',' ');
		content = content.replace('-',' ');
		content = content.replace(':',' ');
		content = content.replace(k,' ');
		String[] inputs = content.split("\\s+");
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}}*/

}
class node<X>
{
	public X data;
	public node<X> next;
	public node<X> prev;
	public node<X> getnext()
	{
		return next;
	}
	public void setnext(node<X> n)
	{
		next = n;
	}
	public node<X> getprev()
	{
		return prev;
	}
	public void setprev(node<X> n)
	{
		prev = n;
	}
	public X getdata()
	{
		return data;
	}
	public void setdata(X n)
	{
		data = n;
	}
	
}