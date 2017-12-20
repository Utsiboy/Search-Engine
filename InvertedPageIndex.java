import java.util.*;
import java.io.*;
 public class InvertedPageIndex
{
	public MyHashTable hashtableforindex = new MyHashTable();
	public Myset<PageEntry> pages = new Myset<PageEntry>();
	public void addPage(PageEntry p)
	{
		pages.addElement(p);
		node<WordEntry> a = p.page.wordentries.head;
		while(a!=null)
		{
			WordEntry w = a.data;
			hashtableforindex.addPositionsForWord(w);
			a = a.next;
		}
	}
	public Myset<PageEntry> getPagesWhichContainWord(String str)
	{
		if(!hashtableforindex.IsPresent(str))
			return null;
		WordEntry wantedword = hashtableforindex.find(str);
		Myset<PageEntry> returnset = wantedword.getPagesWhichContainWord(wantedword.positionlist);
		
		Myset<PageEntry> returnlist = new Myset<PageEntry>();
		node<PageEntry> pablo = returnset.head;
		while(pablo!=null)
		{
			//System.out.println("br");
			if(returnlist.head==null)
				{
					node<PageEntry> c = new node<PageEntry>();
					c.data = pablo.data;
					returnlist.head = returnlist.tail = c;returnlist.mysetsize++;
					//return;
				}
				else
				{
					//System.out.println("br");
					node<PageEntry> c = new node<PageEntry>();
				c.data = pablo.data;
				node<PageEntry> check = returnlist.head;
				PageEntry alpha = check.data;
				WordEntry w = alpha.throwword(str);
				float relevance = alpha.relevanceforword(w);
				//System.out.println(relevance);
				PageEntry beta = c.data;
				WordEntry wdash = beta.throwword(str);
				float givenrelevance = beta.relevanceforword(wdash);
			//System.out.println("br");

				while((check!=null)&&(relevance>givenrelevance))
				{
					//System.out.println("br");
					check = check.next;
					if(check!=null)
					{alpha = check.data;
					 w = alpha.throwword(str);
					 relevance = alpha.relevanceforword(w);}
				}//System.out.println("br");
				if(check==null)
				{
					//System.out.println("br");
					returnlist.tail.next =c;
					c.prev=returnlist.tail;
					returnlist.tail = c;
				}
				else
				{
					//System.out.println("br");
					if(check==returnlist.head)
					{
						c.next = check;
						check.prev =c;
						returnlist.head = c;
					}
					else{
					c.next = check;
					c.prev = check.prev;
					check.prev.next = c;
					check.prev = c;}
				}
				returnlist.mysetsize++;

				}
				pablo = pablo.next;
		}
		/*if(returnlist.head==null)
				{
					node<PageEntry> c = new node<PageEntry>();
					c.data = element;
					returnlist.head = returnlist.tail = c;returnlist.linkedlistsize++;
					return;
				}
				node<PageEntry> c = new node<PageEntry>();
				c.data = element;
				tail.setnext(c);
				c.setprev(tail);
				tail = c;
				returnlist.linkedlistsize++;*/

		//PageEntry[] pageswanted = new PageEntry[returnset.mysetsize];
		return returnlist;
	}
	public Myset<PageEntry> getPagesWhichContainAnyOfTheseWords(String str[])
	{
		Myset<PageEntry> orqueries = new Myset<PageEntry>();
		for(int i=0;i<str.length;i++)
		{
			Myset<PageEntry> bla;
			if(this.hashtableforindex.IsPresent(str[i]))
			 bla = this.getPagesWhichContainWord(str[i]);
			else continue;
			if(orqueries!=null)
			orqueries = orqueries.Union(bla);
		else 
			orqueries = bla;
		}
		return orqueries;
	}
	public Myset<PageEntry> getPagesWhichContainAllWords(String str[])
	{
		Myset<PageEntry> orqueries = new Myset<PageEntry>();
		orqueries = this.getPagesWhichContainWord(str[0]);
		if(orqueries==null)
			return null;
		for(int i=1;i<str.length;i++)
		{
			Myset<PageEntry> bla;
			if(this.hashtableforindex.IsPresent(str[i]))
			 {
			 	//System.out.println("booo");
			 	bla = this.getPagesWhichContainWord(str[i]);}
			else 
			{
				//System.out.println("booo");
				continue;
			}
			//System.out.println(this.getPagesWhichContainWord(str[i]).head);
			
			orqueries = orqueries.Intersection(bla);
		
		}
		return orqueries;
	}
	public Myset<PageEntry> getPagesWhichContainPhrase(String str[])
	{

		Myset<PageEntry> firstwordpages = this.getPagesWhichContainAllWords(str);
		if(firstwordpages==null)
			return null;
		//Myset<PageEntry> phraseset = new Myset<PageEntry>();
		node<PageEntry> firsthead = firstwordpages.head;
		while(firsthead!=null)
		{
			PageEntry p = firsthead.data;
			float n = p.getRelevanceOfPage(str,true);
			//System.out.println(n);
			//System.out.println(n==0);
			if(n==0.0)
			{firstwordpages.delete(p);
				//System.out.println("delete");
			}
			firsthead = firsthead.next;
		}
		return firstwordpages;
		
	}
	public PageEntry throwpage(String str)
	{
		node<PageEntry> n = pages.head;
		while(n!=null)
		{
			if(n.data.pagename.equals(str))
				return n.data;
			n = n.next;
		}
		return null;
	}
}
/*class relevanceset
{
	public int relevance;
	public PageEntry relevantpage;
}*/
class MyHashTable
{
	public step[] HashTable = new step[1000];
	private int getHashIndex(String str)
	{
		int hash = 7;
		for(int i=0;i<str.length();i++)
		{
			hash = (hash*17 + str.charAt(i))%1000;
		}
		hash  = (hash)%(1000);
		return hash;
	}
	public void addPositionsForWord(WordEntry w)
	{
		String str = w.word;
		int i = this.getHashIndex(str);

		if(HashTable[i]==null)
		{
			step  s = new step();
			s.data = w;
			s.prev=null;
			s.next = null;
			HashTable[i] = s;
			//System.out.println("br");
		}
		else
		{
			step s = HashTable[i];
			AVLTree newpositions =w.positionlist;
			while(s!=null)
				{
					if(s.data.word.equals(w.word))
					{
						s.data.addPositions(newpositions);
						return;
					}
					s = s.next;
				}
				s = HashTable[i];
				while(s.next!=null)
				{
					s = s.next;
				}
			step dash = new step();
			dash.data = w;
			dash.prev = s;
			s.next = dash;
			//dash.next = null;
			//HashTable[i] = s;
			//System.out.println("br");

		}
	}
	public boolean IsPresent(String str)
	{
		int i = this.getHashIndex(str);
		if(HashTable[i]==null)
			{//System.out.println("br");
			return false;}
		else
		{
			step s = HashTable[i];
			while(s!=null)
			{
				if(s.data.word.equals(str))
					return true;
				s = s.next;
			}
			return false;
		}
	}
	public WordEntry find(String str)
	{
		int i = this.getHashIndex(str);
		step s = HashTable[i];
			while(s!=null)
			{
				if(s.data.word.equals(str))
					return s.data;
				s = s.next;
			}
			return s.data;
	}
}
class step
{
	public WordEntry data;
	public step next;
	public step prev;
}
class Position
{
	public PageEntry page;
	public int wordIndex;
	public Position(PageEntry p,int wordIndex)
	{
		page = p;
		this.wordIndex = wordIndex;
	}
	public PageEntry getPageEntry()
	{
		return page;
	}
	public int getWordIndex()
	{
		return wordIndex;
	}
}
class PageIndex
{
	public MyLinkedList<WordEntry> wordentries = new MyLinkedList<WordEntry>();
	public boolean IsMember(String element)
	{
		if (wordentries.head == null)
			return false;
		else
		{
			node<WordEntry> a = wordentries.head;
			while(a!= null)
			{
				if(a.data.word.equals(element))
					return true;
				a = a.next;
			}
			return false;
		}
	}
	public void addPositionForWord(String str, Position p)
	{
		if(this.IsMember(str))
		{
			node<WordEntry> a = wordentries.head;
			while(a!= null)
			{
				if(a.data.word.equals(str))
				{
					a.data.addPosition(p);
					break;
				}
				a = a.next;
			}
		}
		else
		{
			node<WordEntry> a = new node<WordEntry>();
			a.data = new WordEntry(str);
			a.data.addPosition(p);
			if(wordentries.head==null)
			{
				wordentries.head = wordentries.tail = a;
			}
			else
			{
				a.prev = wordentries.tail;
				wordentries.tail.next = a;
				wordentries.tail = a;

			}
		}
	}
	public MyLinkedList<WordEntry> getWordEntries()
	{
		return wordentries;
	}
	

}
class PageEntry
{
	public PageIndex page = new PageIndex();
	public int words;
	public String pagename;
	public PageEntry(String pageName)
	{
		pagename = pageName;
		try{String content = new Scanner(new FileInputStream(pageName)).useDelimiter("\\Z").next();

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
		for(int i=0;i<inputs.length;i++)
		{
			String str = inputs[i].toLowerCase();
			Position p = new Position(this,i+1);
			if(this.isnoteligible(str))
			{
				continue;
			}
			else if(str.equals("stacks"))
			{
				str = "stack";
			}
			else if(str.equals("structures"))
			{
				str = "structure";
			}
			else if(str.equals("applications"))
			{
				str = "application";
			}
			page.addPositionForWord( str,  p);
			
		}
		words = inputs.length;
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}

	}
	public boolean isnoteligible(String str)
	{
		String[] strarr = { "a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of",
				"or", "and", "does", "will", "whose" };
			for(int j=0;j<strarr.length;j++)
			{
				if(str.equals(strarr[j]))
					return true;
			}
			return false;
	}
	public PageIndex getPageIndex()
	{
		return page;
	}
	public float relevanceforword(WordEntry givenword)
	{
		//System.out.println(givenword.positionlist.root);
		//return givenword.relevanceforword(this, givenword.positionlist);
		if(givenword==null)
			return (float)(0.0);


		return (givenword.positionlist.relevance(this));
		 
	}
	public float getRelevanceOfPage(String str[ ], boolean doTheseWordsRepresentAPhrase)
	{
		float relevance = (float)(0.0);
		if(doTheseWordsRepresentAPhrase)
		{
			AVLTree phrasetree = this.throwword(str[0]).positionlist;
			float n =  phrasetree.relevanceforphrase(str,this);
			//System.out.println(n);
			return n;
		}
		else
		{
			for(int i=0;i<str.length;i++)
			{
				relevance = relevance + relevanceforword(this.throwword(str[i]));
			}
		}
		return relevance;
	}

	public WordEntry throwword(String str)
	{
		node<WordEntry> a = page.wordentries.head;
		while(a!= null)
			{
				if(a.data.word.equals(str))
				{
					return a.data;
				}
				a = a.next;
			}
			return null;
	}
	public boolean IsPhrase(String str[],Position posofphrase)
	{
		Position pos = posofphrase;
		//int actualpos = posofphrase.wordIndex;
			for(int i=0;i<str.length-1;i++)
			{
		 		//WordEntry w = this.throwword(str[i]);
				WordEntry wnext = this.throwword(str[i+1]);
				//AVLTree t = w.positionlist;
				//AVLTree tnext = wnext.positionlist;
				if(this.nextofword(pos)==null)
					return false;
				if(!(this.nextofword(pos).equals(wnext)))
					return false;
				pos = this.nextpos(pos);
				//actualpos = actualpos+1;
			}
			return true;
	}
	public WordEntry nextofword( Position p)
	{
		node<WordEntry> listhead = this.page.wordentries.head;
		WordEntry minword = null;
		int minvalue = words;
		while(listhead!=null)
		{
			for(int i=p.wordIndex+1;i<=words;i++)
			{
				if((listhead.data.positionlist.IsMember(this,i))&&(i<minvalue))
				{
					minword = listhead.data;
					minvalue = i;
				}
			}
			listhead = listhead.next;
		}
		return minword;
	}
	public Position nextpos( Position p)
	{
		node<WordEntry> listhead = this.page.wordentries.head;
		WordEntry minword = null;
		int minvalue = words;
		while(listhead!=null)
		{
			for(int i=p.wordIndex+1;i<=words;i++)
			{
				if((listhead.data.positionlist.IsMember(this,i))&&(i<minvalue))
				{
					minword = listhead.data;
					minvalue = i;
				}
			}
			listhead = listhead.next;
		}
		return minword.positionlist.memberpos(this,minvalue);
	}

	
}
class WordEntry
{
	public String word;
	public AVLTree positionlist = new AVLTree();
	public WordEntry(String word)
	{
		this.word = word;
	}
	public void addPosition(Position position)
	{
		positionlist.insert(position); 
	}
	public void addPositions(AVLTree positions)
	{
		AVLNode a = positions.root;
		if(a!=null)
		{
			this.addPosition(a.data);
			this.addPositions(a.leftsubtree());
			this.addPositions(a.rightsubtree());
		}
	}
	public AVLTree getAllPositionsForThisWord()
	{
		return positionlist;
	}
	/*public float relevanceforword(PageEntry p, AVLTree t)
	{
		float relevance = (float)(0.0);
		System.out.println(t);
		System.out.println(t.root);
		AVLNode wordhead = t.root;
		//if (wordhead==null)System.out.println("yezs");
		if(wordhead!=null)
		{if(p.equals(wordhead.data.page))
		{
			relevance = relevance + relevanceforword(p,wordhead.leftsubtree());
			float inter = (float)(wordhead.data.wordIndex);
			float minter = (inter*inter);
			float midinter = ((float)(1.0))/minter;
			relevance = relevance + midinter;
			//wordhead = wordhead.next;
			//System.out.println(""+wordhead.data);
			
			relevance = relevance + relevanceforword(p,wordhead.rightsubtree());
		}}
		return relevance;
	}*/
	public Myset<PageEntry> getPagesWhichContainWord(AVLTree t)
	{
		Myset<PageEntry> returnset = new Myset<PageEntry>();
		AVLNode a = t.root;
		if(a!=null)
		{returnset.addElement(a.data.page);
		returnset = returnset.Union(this.getPagesWhichContainWord(a.leftsubtree()));
		returnset = returnset.Union(this.getPagesWhichContainWord(a.rightsubtree()));}
		return returnset;
	}
}
class AVLNode
 {    
     AVLNode left, right;
     Position data;
     int height;
 
     /* Constructor */
     public AVLNode()
     {
         left = null;
         right = null;
         data = null;
         height = 0;
     }
     /* Constructor */
     public AVLNode(Position n)
     {
         left = null;
         right = null;
         data = n;
         height = 0;
     }  
     public AVLTree leftsubtree()
     {
     	AVLTree lefttree= new AVLTree();
     	lefttree.root = this.left;
     	return lefttree; 
     } 
     public AVLTree rightsubtree()
     {
     	AVLTree righttree =  new AVLTree();
     	righttree.root = this.right;
     	return righttree;
     }  
 }
 
 
 class AVLTree
 {
     public AVLNode root;     
 
     
     /*public AVLTree()
     {
         root = null;
     }*/
     
     public boolean isEmpty()
     {
         return root == null;
     }
     
     
     
     
     public int height(AVLNode t )
     {
         if(t==null)
         	return -1;
         else
         	return t.height;
     }
     
     public int max(int lhs, int rhs)
     {
         if(lhs>rhs)
         	return lhs;
         else 
         	return rhs;
     }
    public void insert(Position data)
     {
         root = insert(data, root);
     } 
     private AVLNode insert(Position x, AVLNode t)
     {
         if (t == null)
             t = new AVLNode(x);
         else if (x.wordIndex < t.data.wordIndex)
         {
             t.left = insert( x, t.left );
             if( height( t.left ) - height( t.right ) == 2 )
                 if( x.wordIndex < t.left.data.wordIndex )
                     t = rotateWithLeftChild( t );
                 else
                     t = doubleWithLeftChild( t );
         }
         else if( x.wordIndex >= t.data.wordIndex )
         {
             t.right = insert( x, t.right );
             if( height( t.right ) - height( t.left ) == 2 )
                 if( x.wordIndex >= t.right.data.wordIndex)
                     t = rotateWithRightChild( t );
                 else
                     t = doubleWithRightChild( t );
         }
         
         t.height = max( height( t.left ), height( t.right ) ) + 1;
         return t;
     }
         
     public AVLNode rotateWithLeftChild(AVLNode k2)
     {
         AVLNode k1 = k2.left;
         k2.left = k1.right;
         k1.right = k2;
         k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
         k1.height = max( height( k1.left ), k2.height ) + 1;
         return k1;
     }
 
     
     public AVLNode rotateWithRightChild(AVLNode k1)
     {
         AVLNode k2 = k1.right;
         k1.right = k2.left;
         k2.left = k1;
         k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
         k2.height = max( height( k2.right ), k1.height ) + 1;
         return k2;
     }
     
     public AVLNode doubleWithLeftChild(AVLNode k3)
     {
         k3.left = rotateWithRightChild( k3.left );
         return rotateWithLeftChild( k3 );
     }
     
     public AVLNode doubleWithRightChild(AVLNode k1)
     {
         k1.right = rotateWithLeftChild( k1.right );
         return rotateWithRightChild( k1 );
     }
     public void traversal(PageEntry p)
     {
     	AVLNode a = root;
     	if(a!=null)
     	{
     		a.leftsubtree().traversal(p);
     		if(a.data.page.equals(p))
     		{
     			System.out.print(" "+ a.data.wordIndex + ",");
     		}
     		a.rightsubtree().traversal(p);
     	}
     }
     public float relevance(PageEntry p)
     {
     	float relevance = (float)(0.0);
     	AVLNode a = root;
     	if(a!=null)
     	{
     		relevance = relevance + a.leftsubtree().relevance(p);
     		if(a.data.page.equals(p))
     		{
     			float inter = (float)(a.data.wordIndex);
			float minter = (inter*inter);
			float midinter = ((float)(1.0))/minter;
			relevance = relevance + midinter;
     		}
     		relevance = relevance + a.rightsubtree().relevance(p);
     	}
     	return relevance;
     }
     public float relevanceforphrase(String str[],PageEntry p)
     {
     	float relevance = (float)(0.0);
     	AVLNode a = root;
     	if(a!=null)
     	{
     		relevance = relevance + a.leftsubtree().relevanceforphrase(str,p);
     		if(a.data.page.equals(p)&&(p.IsPhrase(str,a.data)))
     		{
     			float inter = (float)(a.data.wordIndex);
			float minter = (inter*inter);
			float midinter = ((float)(1.0))/minter;
			relevance = relevance + midinter;
     		}
     		relevance = relevance + a.rightsubtree().relevanceforphrase(str,p);
     	}
     	//System.out.println(relevance);
     	return relevance;
     }
     public boolean IsMember(PageEntry p, int randoms)
     {
     	AVLNode a = root;
     	boolean b = false;
     	if(a!=null)
     	{
     		
     		if((a.data.page.equals(p))&&(a.data.wordIndex==(randoms)))
     			b = b||(true);
     		else
     			b = b||(false);
     		
     		b = b||a.leftsubtree().IsMember(p,randoms);
     		b = b||a.rightsubtree().IsMember(p,randoms);
     	}
     	return b;
     }
     public Position memberpos(PageEntry p, int randoms)
     {
     	AVLNode a = root;
     	Position pos = null;
     	if(a!=null)
     	{
     		if((a.data.page.equals(p))&&(a.data.wordIndex==(randoms)))
     			return a.data;
     		if(pos==null)
     		pos = a.leftsubtree().memberpos(p,randoms);
     		if(pos==null)
     			pos = a.rightsubtree().memberpos(p,randoms);
     	}
     	return pos;

     }
     public boolean IsNext(AVLTree scnd)
     {
     	AVLNode a = root;
     	boolean b = false;
     	if(a!=null)
     	{
     		
     		PageEntry p = a.data.page;
     		int randoms  = a.data.wordIndex;
     		if(scnd.IsMember(p,randoms+1))
     			b =  b||true;
     		else b =  b||false;
     		b = b||a.leftsubtree().IsNext(scnd);
     		b = b||a.rightsubtree().IsNext(scnd);
     	}
     	return b;
     }
 }
 class SearchResult implements Comparable<SearchResult>
 {
 	public PageEntry page;
 	public float relevance;
 	public SearchResult(PageEntry p, float r)
 	{
 		page = p;
 		relevance = r;
 	}
 	public PageEntry getPageEntry()
 	{
 		return page;
 	}
 	public float getRelevance()
 	{
 		return relevance;
 	}
 	public int compareTo(SearchResult otherObject)
 	{
 		if(relevance== otherObject.relevance)
 			return 0;
 		else if(relevance> otherObject.relevance)
 			return 1;
 		else
 			return -1;
 	}
 }
 class MySort
 {
 	public ArrayList<PageEntry> sortThisList(Myset<SearchResult> listOfSortableEntries)
 	{
 		ArrayList<PageEntry> returnlist = new ArrayList<PageEntry>();
 		while(!listOfSortableEntries.IsEmpty())
 		{
 			node<SearchResult> returnhead = listOfSortableEntries.head;
 			node<SearchResult> max = returnhead;
 			while(returnhead!=null)
 			{
 				if(returnhead.data.compareTo(max.data)>=0)
 					max = returnhead;
 				returnhead = returnhead.next;
 			}
 			returnlist.add(max.data.getPageEntry());
 			listOfSortableEntries.delete(max.data);
 		}
 		return returnlist;
 	}
 }