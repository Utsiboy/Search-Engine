public class MyLinkedList<A>
{
	public node<A> head;
	public node<A> tail;
	public int linkedlistsize;
	public boolean isEmpty()
	{
		return head==null;
	}
	public boolean IsMember(A element)
	{
		if (head == null)
			return false;
		else
		{
			node<A> a = head;
			while(a!= null)
			{
				if(a.data.equals(element))
					return true;
				a = a.next;
			}
			return false;
		}
	}
	public void insert(A element)
	{
		if(head==null)
		{
			node<A> a = new node<A>();
			a.data = element;
			head = tail = a;
		}
		else
		{
			node<A> a = new node<A>();
			a.data = element;
			a.prev = tail;
			tail.next = a;
			tail = a;
		}
		linkedlistsize++;
	}
	public A deleteAtlast()
	{
		try
		{if(head==null)
			throw new Exception();
				
			else
			{
				if(head.next==null)
				{
					A element = tail.data;
					head = tail = null;
					linkedlistsize--;
					return element;
				}
				else 
				{
					A element = tail.data;
					tail = tail.prev;
					tail.next = null;
					linkedlistsize--;
					return element;
				}
			}
			}
			catch(Exception e)
			{e.printStackTrace();}
			return null;	
	}
	public MyLinkedList<A> insertlist(MyLinkedList<A> list)
	{
		MyLinkedList<A> newlist = new MyLinkedList<A>();
		newlist.head = this.head;
		this.tail.next = list.head;
		list.head.prev = this.tail;
		newlist.tail = list.tail;
		newlist.linkedlistsize = this.linkedlistsize + list.linkedlistsize;
		return newlist;
	}
	
	
}
/*class node<A>
{
	public A data;
	public node<A> next;
	public node<A> prev;
	public node<A> getnext()
	{
		return next;
	}
	public void setnext(node<A> n)
	{
		next = n;
	}
	public node<A> getprev()
	{
		return prev;
	}
	public void setprev(node<A> n)
	{
		prev = n;
	}
	public A getdata()
	{
		return data;
	}
	public void setdata(A n)
	{
		data = n;
	}
}*/