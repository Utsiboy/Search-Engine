import java.util.*;
public class SearchEngine{
	public InvertedPageIndex index ;//= new InvertedPageIndex();

	public SearchEngine() {
		index = new InvertedPageIndex();
		// ...
	}

	public void performAction(String actionMessage) 
	{
		String[] inputs = actionMessage.split("\\s+");
			String a = inputs[0];
			if(a.equals("addPage"))
			{

				String s = inputs[1];
				//System.out.println(s);
				if(index.throwpage(s)==null)
				{PageEntry p = new PageEntry(s);
				index.addPage(p);
				System.out.println(actionMessage);}
				else
				{
					System.out.println("Page already added");
				}
			}
			else if(a.equals("queryFindPagesWhichContainWord"))
			{
				String s = inputs[1].toLowerCase();
				//System.out.println(s);
				if(s.equals("stacks"))
			{
				s = "stack";
			}
			else if(s.equals("structures"))
			{
				s = "structure";
			}
			else if(s.equals("applications"))
			{
				s = "application";
			}
			//System.out.println(index.hashtableforindex.IsPresent(s));
				if(!index.hashtableforindex.IsPresent(s))
				{
					System.out.println(actionMessage + " No webpage contains word "+ s);
				}

				else
				{
					Myset<PageEntry> pageset = index.getPagesWhichContainWord(s);
					node<PageEntry> headnode = pageset.head;
					System.out.print(actionMessage+" ");
					while(headnode!=null)
					{
						System.out.print(headnode.data.pagename+",");
						headnode = headnode.next;
						//System.out.println("br");
					}
					System.out.println("");
				}
			}
			else if(a.equals("queryFindPositionsOfWordInAPage"))
			{
				String wordreq = inputs[1].toLowerCase();
				String pagereq = inputs[2];
				if(wordreq.equals("stacks"))
			{
				wordreq = "stack";
			}
			else if(wordreq.equals("structures"))
			{
				wordreq = "structure";
			}
			else if(wordreq.equals("applications"))
			{
				wordreq = "application";
			}
				
				if(index.throwpage(pagereq)==null)
					System.out.println(actionMessage+" No such webpage found");
				else
				{
					PageEntry p = index.throwpage(pagereq);
					node<WordEntry> wordnode = p.page.wordentries.head;
					AVLTree positionsofwordreq = new AVLTree();
					positionsofwordreq = null;
					while(wordnode!=null)
					{
						if(wordnode.data.word.equals(wordreq))
						{
							 positionsofwordreq = wordnode.data.positionlist;
							 break;
						}
						wordnode = wordnode.next;
					}
					if(positionsofwordreq==null)
					{
						System.out.println(actionMessage+" No such word found in webpage "+pagereq );
					}
					else
					{
						System.out.print(actionMessage);
						//node<Position> positionsreq = positionsofwordreq.head;
						positionsofwordreq.traversal(p);
						//System.out.println(p.relevanceforword(wordnode.data));
						System.out.println("");
					}
				}
			}
			else if(a.equals("queryFindPagesWhichContainAllWords"))
			{
				String[] str = new String[inputs.length-1];
				for(int i=0;i<str.length;i++)
				{
					str[i] = inputs[i+1].toLowerCase();
					if(str[i].equals("stacks"))
			{
				str[i] = "stack";
			}
			else if(str[i].equals("structures"))
			{
				str[i] = "structure";
			}
			else if(str[i].equals("applications"))
			{
				str[i] = "application";
			}
			//System.out.println(str[i]);
				
				}
				Myset<PageEntry> Andnormalset = index.getPagesWhichContainAllWords(str);
				if(Andnormalset==null)
				{
					System.out.print(actionMessage);
					System.out.println(" No webpage contains all words");
				}
				else{
				//System.out.println(Andnormalset.head);
				//float[] relevances = new float[Andnormalset.mysetsize];
				node<PageEntry> andhead = Andnormalset.head;
				int j = 0;
				Myset<SearchResult> Andorderedset = new Myset<SearchResult>();

				while(andhead!=null)
				{
					//System.out.println("hrl\n");
					SearchResult andsearch = new SearchResult(andhead.data,andhead.data.getRelevanceOfPage(str, false));
					
					Andorderedset.addElement(andsearch);
					j++;
					andhead = andhead.next;
				}
				System.out.print(actionMessage);
				if(Andorderedset.head==null)
					System.out.println(" No webpage contains all words");
				else
				{MySort sorting = new MySort();
				//ArrayList<PageEntry> returnlist = sorting.sortThisList(Andorderedset);
				
				
				//if(returnlist!=null)
				 Iterator<PageEntry> itr=sorting.sortThisList(Andorderedset).iterator();
				//System.out.println("hrl\n");
					 while(itr.hasNext())
					{
						//System.out.println("hrl\n");
						System.out.print(" ,"+(itr.next().pagename));
						
					}
					System.out.println("");
				}}

			}
			else if(a.equals("queryFindPagesWhichContainAnyOfTheseWords"))
			{
				String[] str = new String[inputs.length-1];
				for(int i=0;i<str.length;i++)
				{
					str[i] = inputs[i+1].toLowerCase();
					if(str[i].equals("stacks"))
			{
				str[i] = "stack";
			}
			else if(str[i].equals("structures"))
			{
				str[i] = "structure";
			}
			else if(str[i].equals("applications"))
			{
				str[i] = "application";
			}
			//System.out.println(str[i]);
				
				}
				Myset<PageEntry> Ornormalset = index.getPagesWhichContainAnyOfTheseWords(str);
				if(Ornormalset==null)
				{
					System.out.print(actionMessage);
					System.out.println(" No webpage contains any of these words");

				}
				else{
				//System.out.println(Andnormalset.head);
				//float[] relevances = new float[Andnormalset.mysetsize];
				node<PageEntry> andhead = Ornormalset.head;
				int j = 0;
				Myset<SearchResult> Ororderedset = new Myset<SearchResult>();

				while(andhead!=null)
				{
					//System.out.println("hrl\n");
					SearchResult andsearch = new SearchResult(andhead.data,andhead.data.getRelevanceOfPage(str, false));
					
					Ororderedset.addElement(andsearch);
					j++;
					andhead = andhead.next;
				}
				System.out.print(actionMessage);
				if(Ororderedset.head==null)
					System.out.println(" No webpage contains any of these words");
				else
				{MySort sorting = new MySort();
				//ArrayList<PageEntry> returnlist = sorting.sortThisList(Andorderedset);
				
				
				//if(returnlist!=null)
				 Iterator<PageEntry> itr=sorting.sortThisList(Ororderedset).iterator();
				//System.out.println("hrl\n");
					 while(itr.hasNext())
					{
						//System.out.println("hrl\n");
						System.out.print(" ,"+(itr.next().pagename));
						
					}
					System.out.println("");
				}}
			}
			else if(a.equals("queryFindPagesWhichContainPhrase"))
			{
				String[] str = new String[inputs.length-1];
				for(int i=0;i<str.length;i++)
				{
					str[i] = inputs[i+1].toLowerCase();
					if(str[i].equals("stacks"))
			{
				str[i] = "stack";
			}
			else if(str[i].equals("structures"))
			{
				str[i] = "structure";
			}
			else if(str[i].equals("applications"))
			{
				str[i] = "application";
			}
			//System.out.println(str[i]);
				
				}
			
			Myset<PageEntry> Phrasenormalset = index.getPagesWhichContainPhrase(str);
			if(Phrasenormalset==null)
				{System.out.print(actionMessage);
					System.out.println(" No webpage contains all words");}
				//System.out.println(Andnormalset.head);
				//float[] relevances = new float[Andnormalset.mysetsize];
					else{
				node<PageEntry> andhead = Phrasenormalset.head;
				int j = 0;
				Myset<SearchResult> Phraseorderedset = new Myset<SearchResult>();

				while(andhead!=null)
				{
					//System.out.println("hrl\n");
					SearchResult andsearch = new SearchResult(andhead.data,andhead.data.getRelevanceOfPage(str, true));
					
					Phraseorderedset.addElement(andsearch);
					j++;
					andhead = andhead.next;
				}
				System.out.print(actionMessage);
				if(Phraseorderedset.head==null)
					System.out.println(" No webpage contains all words");
				else
				{MySort sorting = new MySort();
				//ArrayList<PageEntry> returnlist = sorting.sortThisList(Andorderedset);
				
				
				//if(returnlist!=null)
				 Iterator<PageEntry> itr=sorting.sortThisList(Phraseorderedset).iterator();
				//System.out.println("hrl\n");
					 while(itr.hasNext())
					{
						//System.out.println("hrl\n");
						System.out.print(" ,"+(itr.next().pagename));
						
					}
					System.out.println("");
				}}
			}

	}
}
