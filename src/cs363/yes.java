//Author: Sam Lundgren
//CS 352
//Project 2
/*
I was unable to develop much more than what I have. I put the project off for too long and am going to have to suffer the consequences to it. 
*/
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>
#include <iostream>
#include <algorithm>
#include <pthread.h>
#include <queue>
using namespace std;
//Setting up the node Struck with parent, left, and right
enum theColor {RED, BLACK};
struct Node{
    int keyToCity;
    Node *rightyTighty, *leftyLoosy, *padre;
    string certainColor;
    Node(int keyToCity)
    {
        this->keyToCity = keyToCity;
        leftyLoosy = rightyTighty = padre = NULL;
        this->certainColor = "b";
    };

};
//setting up my tree, was not able to do much more than this
class TreeClass
{
    public:
    queue<string> theReaders;
    queue<string> theWriters;
    vector<Node> theNodes;
    int threadsOfSearch = 0;
    int threadsOfModify = 0;
    vector<string> storageC;
};
//names for my methods, was not able to work on these
TreeClass myTree;
void nodeToInsert(Node to_insert);
bool theSearchTool(Node to_search);
bool theSearchTool(Node to_search);
void extractData(char *s);
//method for extracting all data from the file input
/**
*
*@param char *s
*/
void extractData(char * s)
{
	//String checkers to check to see whether it is insert, delete, or search.
string cheese;
ifstream myTreeIn (s);
string searchNum1 = "Test";
string searchNum2 = ",";
string theThreadSearcher = "Search threads: ";
string theModifySearcher = "Modify threads: ";
string theInsertSearcher = "insert";
string theDeleteSearcher = "delete";
string theSearchSearcher = "search";
//check if open
if(myTreeIn.is_open())
{
	//go through each line and try to locate insert, search or delete
    while (getline(myTreeIn,cheese))
    {
        size_t locateIt = cheese.find(searchNum1);
        size_t locateItNodes = cheese.find(searchNum2);
        size_t locateItT = cheese.find(theThreadSearcher);
        size_t locateItS = cheese.find(theSearchSearcher);
        size_t locateItI = cheese.find(theInsertSearcher);
        size_t locateItD = cheese.find(theDeleteSearcher);      
        if (locateIt != std::string::npos )
        {
           // cout<<"Test found"<<endl;
            continue;
        }  

		//If it is f
else if(locateItNodes != std::string::npos || cheese.front() == 'f')
{
	//create vector in order to store data
vector<string> myVector;
string stringCheck = cheese;
cout<< stringCheck <<endl;
stringCheck.erase(remove(stringCheck.begin(), stringCheck.end(), ' '), stringCheck.end());
stringstream myList(stringCheck);
int myNum;
    while (myList.good())
    {     
        string myNode;
        getline(myList, myNode, ',');
        if(myNode.length() > 0)
        {
            if(myNode.substr(0, myNode.length()).compare("f") == 0)
            {
                Node nullChecker(-1);
                myTree.theNodes.push_back(nullChecker);                     
                continue;
            }
            else
            {            
                if(myNode.at(0) == 'f')
                {                    
                    Node nullChecker(-1);
                    myTree.theNodes.push_back(nullChecker);                   
                    continue;
                }
            Node checkNodeG(stoi(myNode.substr(0, myNode.length())));
            checkNodeG.certainColor = myNode.back();            
            int k = stoi(myNode.substr(0, myNode.length()));
             myTree.theNodes.push_back(checkNodeG);
            }
            //cout<<endl;
        }
        myVector.push_back(myNode);
    } 
    }
	//check if first character is S, for Search Threads print out
      else if (cheese.at(0) == 'S')
        {         
            cout<<cheese.substr(16,cheese.length()-1);
            int searchingN = stoi(cheese.substr(16,cheese.length()-1));
            myTree.threadsOfSearch = searchingN;         
        }
		//check if character is M, for Modify Threads print out
        else if (cheese.at(0) == 'M')
        {
            cout<<cheese.substr(16,cheese.length()-1);
            int searchingM = stoi(cheese.substr(16,cheese.length()-1));
            myTree.threadsOfModify = searchingM;
			}
       
	   //degin delimiting by ||
        else if(locateItD != std::string::npos || locateItS != std::string::npos | locateItI != std::string::npos )
        {
            string storageC = cheese;
			//quick little way to delete all white space in order to parse easier.
            storageC.erase(remove(storageC.begin(), storageC.end(), ' '), storageC.end());
            stringstream myListC(storageC);           
            size_t thePositions = 0;
            string myTok;
			//set delimiter ||
            string del = "||";
            while ((thePositions = storageC.find(del)) != std::string::npos) {
                myTok = storageC.substr(0, thePositions);
                cout << myTok << std::endl;
				//check if first character is S, if it is it is a reader (search)
                if(myTok.at(0) == 's')
                {                    
                    string val = myTok.substr(7,myTok.length()-2);                   
                    int pos1 = val.find(')');
                    myTree.theReaders.push(val.substr(0,pos1));                    
                }        
			//check if first character is d or i, if it is it is a writer (delete,insert)				
            if(myTok.at(0) == 'd' || myTok.at(0)== 'i')
                {                   
                    string val = myTok.substr(7,myTok.length()-2);                   
                    int pos1 = val.find(')');                    
                    myTree.theWriters.push(myTok.substr(0,1)+""+val.substr(0,pos1));
                }
                storageC.erase(0, thePositions + del.length());
            }         
			//get value from 0, to thePositions
            myTok = storageC.substr(0, thePositions);
                cout << myTok << std::endl;
                if(myTok.at(0) == 's')
                {                   
                    string val = myTok.substr(7,myTok.length()-2);                   
                    int pos1 = val.find(')');
                    myTree.theReaders.push(val.substr(0,pos1));                    
                }               
            if(myTok.at(0) == 'd' || myTok.at(0)== 'i')
                {                     
                    string val = myTok.substr(7,myTok.length()-2);                   
                    int pos1 = val.find(')');                   
                    myTree.theWriters.push(myTok.substr(0,1)+""+val.substr(0,pos1));
                }
            cout<<storageC<<endl;
        }
    }
    myTreeIn.close();
}
}
int main(int argc, char *argv[]) {
//call extract data for my data reading from file
if(argc> 1)
{
    extractData(argv[1]);
}
cout<<"Readers: " << endl;
   for(int i=0; i < myTree.theReaders.size(); i++)
   {
       
      cout << " "<< myTree.theReaders.front();
      myTree.theReaders.pop();
}
 cout << " "<< myTree.theReaders.front();
cout<<endl;
cout<<"Writers: " << endl;
 for(int i=0; i < myTree.theWriters.size(); i++)
   {
      cout << " "<< myTree.theWriters.front();
      myTree.theWriters.pop();
   }
 cout << " "<< myTree.theWriters.front();
cout<<endl;
cout<<"The # of Search threads: " << myTree.threadsOfSearch << endl;
cout<<"The # of Modify threads: " << myTree.threadsOfModify << endl;
    return 0;
}
/**
 *
 * @param to_insert
 */
void nodeToInsert(Node to_insert);
bool theSearchTool(Node to_search);