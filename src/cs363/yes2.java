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



enum theColor {RED, BLACK};
struct Node
{
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

TreeClass myTree;
void nodeToInsert(Node to_insert);
bool theSearchTool(Node to_search);
bool theSearchTool(Node to_search);
void extractData(char *s);
int main(int argc, char *argv[]) {

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
//  for(int i=0; i < myTree.theNodes.size(); i++)
//    {
//        cout<<endl;
//       cout << myTree.theNodes.at(i).keyToCity<< " : " << myTree.theNodes.at(i).color;
//    }

cout<<endl;
cout<<"The # of Search threads: " << myTree.threadsOfSearch << endl;
cout<<"The # of Modify threads: " << myTree.threadsOfModify << endl;
    return 0;
}

void extractData(char * s)
{
string cheese;
ifstream myTreeIn (s);
string searchNum1 = "Test";
string search2 = ",";
string searchThreads = "Search threads: ";
string modifyThreads = "Modify threads: ";
string searchInsert = "insert";
string searchDelete = "delete";
string searchSearch = "search";
if(myTreeIn.is_open())
{
    while (getline(myTreeIn,cheese))
    {
        size_t found = cheese.find(searchNum1);
        size_t foundnodes = cheese.find(search2);
        size_t searchT = cheese.find(searchThreads);
        size_t searchS = cheese.find(searchSearch);
        size_t searchI = cheese.find(searchInsert);
        size_t searchD = cheese.find(searchDelete);

        // if (found!=std::string::npos
        if (found != std::string::npos )
        {
            cout<<"Test found"<<endl;
            continue;
        }
       

else if(foundnodes != std::string::npos || cheese.front() == 'f')
{
vector<string> vect;
// stringstream nodeslist;
string test = cheese;
cout<< test <<endl;
test.erase(remove(test.begin(), test.end(), ' '), test.end()); 
// cout<<endl;
stringstream nodeslist(test);
int numi;
    while (nodeslist.good())
    {
     
        string nodei;
        getline(nodeslist, nodei, ',');
        if(nodei.length() > 0)
        {
            if(nodei.substr(0, nodei.length()).compare("f") == 0)
            {
                Node nullNode(-1);
                myTree.theNodes.push_back(nullNode);
                // cout<<"Key: "<< "NULL" << endl;
                
                //make null node
                continue;
            }
            else
            {
            
                if(nodei.at(0) == 'f')
                {
                    // cout<<"Key: "<< "NULL" << endl;
                    Node nullNode(-1);
                    myTree.theNodes.push_back(nullNode);
                    //make null node
                    
                    continue;
                }
            // cout<<"Key: "<< nodei.substr(0, nodei.length()-1);
            // cout<<" || Color: " <<nodei.back();
            
            Node goodnode(stoi(nodei.substr(0, nodei.length())));
            goodnode.certainColor = nodei.back();
            // goodnode.keyToCity = stoi(nodei.substr(0, nodei.length()));
            // cout<<stoi(nodei.substr(0, nodei.length()));
            int k = stoi(nodei.substr(0, nodei.length()));
             myTree.theNodes.push_back(goodnode);
            }
            cout<<endl;
        }
        vect.push_back(nodei);
    }
    // break;

    }
      else if (cheese.at(0) == 'S')
        {
         
            cout<<cheese.substr(16,cheese.length()-1);
            int nsearchthreads = stoi(cheese.substr(16,cheese.length()-1));
            myTree.threadsOfSearch = nsearchthreads;
            
     

        }
        else if (cheese.at(0) == 'M')
        {

            cout<<cheese.substr(16,cheese.length()-1);
            int mthreads = stoi(cheese.substr(16,cheese.length()-1));
            myTree.threadsOfModify = mthreads;
            

        }
        //string searchInsert = "insert";
        // string searchDelete = "delete";
        // string searchSearch = "search";
        // size_t searchT = cheese.find(searchThreads);
        // size_t searchS = cheese.find(searchSearch);
        // size_t searchI = cheese.find(searchInsert);
        // size_t searchD = cheese.find(searchDelete);
        else if(searchD != std::string::npos || searchS != std::string::npos | searchI != std::string::npos )
        {
            string storageC = cheese;
            storageC.erase(remove(storageC.begin(), storageC.end(), ' '), storageC.end()); 

            stringstream commandlist(storageC);
            // vector<string> vect;
            // stringstream nodeslist;
            // string test = cheese;
            size_t pos = 0;
            string token;
            string del = "||";
            while ((pos = storageC.find(del)) != std::string::npos) {
                token = storageC.substr(0, pos);
                cout << token << std::endl;
                if(token.at(0) == 's')
                {
                    
                    // cout<<"Search:"<< token;
                    // cout<<"Len of str: " << token.length() << endl;
                    string val = token.substr(7,token.length()-2);
                   
                    int pos1 = val.find(')');
                    myTree.theReaders.push(val.substr(0,pos1));
                    //  cout<<"added: " << val.substr(0,pos1) << " to search"<<endl;
                }
                // storageC.erase(0, pos + del.length());
            
            if(token.at(0) == 'd' || token.at(0)== 'i')
                {
                    
                    // cout<<"Search:"<< token;
                    // cout<<"Len of str: " << token.length() << endl;
                    string val = token.substr(7,token.length()-2);
                   
                    int pos1 = val.find(')');
                    // cout << token.substr(0,1)<<endl;
                    myTree.theWriters.push(token.substr(0,1)+""+val.substr(0,pos1));
                    // cout<<"added: " << myTree.theWriters.back() <<endl;
                }
                storageC.erase(0, pos + del.length());
            }
            // cout << s << std::endl;
            // getline(commandlist, cmd, '||');
            // cout<<"Found storageC: "<< storageC <<endl;

            token = storageC.substr(0, pos);
                cout << token << std::endl;
                if(token.at(0) == 's')
                {
                    
                    // cout<<"Search:"<< token;
                    // cout<<"Len of str: " << token.length() << endl;
                    string val = token.substr(7,token.length()-2);
                   
                    int pos1 = val.find(')');
                    myTree.theReaders.push(val.substr(0,pos1));
                    //  cout<<"added: " << val.substr(0,pos1) << " to search"<<endl;
                }
                // storageC.erase(0, pos + del.length());
            
            if(token.at(0) == 'd' || token.at(0)== 'i')
                {
                    
                    // cout<<"Search:"<< token;
                    // cout<<"Len of str: " << token.length() << endl;
                    string val = token.substr(7,token.length()-2);
                   
                    int pos1 = val.find(')');
                    // cout << token.substr(0,1)<<endl;
                    myTree.theWriters.push(token.substr(0,1)+""+val.substr(0,pos1));
                    // cout<<"added: " << myTree.theWriters.back() <<endl;
                }
            cout<<storageC<<endl;
            break;
        }
    }
    myTreeIn.close();
}




// if(myTreeIn.is_open())
// {
//     while (getline(myTreeIn,cheese))
//     { 
//         cout<<cheese << '\n';
//     }
//     myTreeIn.close();
// }
}
/**
 *
 * @param to_insert
 */
void nodeToInsert(Node to_insert);
bool theSearchTool(Node to_search);