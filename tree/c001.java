import java.util.ArrayList;
import java.util.LinkedList;
public class c001{

    public static void main(String[] args){
        solve();
    }

   public static class Node{
       int data;  
       Node left=null;   // Node* left=nullptr;   
       Node right=null;  // Node* right=nullptr;

       Node(int data){
           this.data=data;
       } 
       
       Node(){
       }
   }

   static int idx=0;
   public static Node constructTree(int[] arr){
      if(idx == arr.length || arr[idx]==-1) {
        idx++;  
        return null;
      }

      Node node=new Node(arr[idx++]);   // Node* node=new Node(arr[idx++]);
      node.left=constructTree(arr);
      node.right=constructTree(arr);
      
      return node;
   }

   public static void display(Node node){
       if(node==null) return;

       String str="";
       str += ((node.left!=null)?node.left.data:".");
       str+=  " <- " + node.data + " -> ";
       str += ((node.right!=null)?node.right.data:".");
       System.out.println(str);

       display(node.left);
       display(node.right);

   }

   //Basic.================================================================

   public static int size(Node node){
     if(node==null) return 0;
     return (size(node.left) + size(node.right) + 1);
   }

   
   public static int height(Node node){
    if(node==null) return -1; // return -1, height w.r.t edge, return 0, height w.r.t node.
    return Math.max(height(node.left), height(node.right)) + 1;
   }

   public static int Maximum(Node node){
    if(node==null) return (int)-1e8;  // java: Integer.MIN_VALUE, c++: INT_MIN; 
    return Math.max(Math.max(Maximum(node.left), Maximum(node.right)),node.data);  // max(leftSubtree,rightSubtree,myself);
   }

   
   public static int Minimum(Node node){
    if(node==null) return (int)1e8;  // java: Integer.MAX_VALUE, c++: INT_MAX; 
    return Math.min(Math.min(Minimum(node.left), Minimum(node.right)),node.data);
   }

   public static int Minimum_02(Node node){
       int min_=node.data;
       
       if(node.left!=null) min_=Math.min(min_,Minimum_02(node.left));
       if(node.right!=null) min_=Math.min(min_,Minimum_02(node.right));
       
       return min_;
   }

   public static boolean find(Node node,int data){
       if(node==null) return false;
       
       if(node.data==data) return true;
       return find(node.left,data) || find(node.right,data);   
        

        // if(find(node.left,data)) return true;
        // if(find(node.rigth,data)) return true;
  
    }

   //Traversal.============================================================

    public static void preOrder(Node node){
         if(node==null) return;

         System.out.print(node.data+ " ");
         preOrder(node.left);
         preOrder(node.right);
    }

    
    public static void inOrder(Node node){
        if(node==null) return;

        inOrder(node.left);
        System.out.print(node.data+ " ");
        inOrder(node.right);
   }

   public static void postOrder(Node node){
    if(node==null) return;

    postOrder(node.left);
    postOrder(node.right);
    System.out.print(node.data+ " ");
   
    }

    public static boolean rootToNodePath_(Node root,int data,ArrayList<Node> path){
      if(root==null) return false;
      if(root.data==data){
          path.add(root);
          return true;
      }

      boolean res = rootToNodePath_(root.left,data,path) || rootToNodePath_(root.right,data,path);
     if(res) path.add(root);
      return res;
    }

    public static ArrayList<Node> rootToNodePath_02(Node root,int data){
        
        if(root==null){
            return new ArrayList<>();
        }
        
        if(root.data==data){
            ArrayList<Node> base=new ArrayList<>();
            base.add(root);
            return base;
        }
  
        ArrayList<Node> left=rootToNodePath_02(root.left,data);
        if(left.size()!=0){
            left.add(root);
            return left;
        }
   
        ArrayList<Node> right=rootToNodePath_02(root.right,data);
        if(right.size()!=0){
            right.add(root);
            return right;
        }
        
        return new ArrayList<>();
      }

    public static void rootToNodePath(Node root,int data){
        ArrayList<Node> path=new ArrayList<>();
        rootToNodePath_(root,data,path);
        for(Node n: path){
            System.out.print(n.data + " -> ");
        }
    }

    public Node lowestCommonAncestor(Node root, int p, int q) {
        ArrayList<Node> path1=new ArrayList<>();
        ArrayList<Node> path2=new ArrayList<>();
        
        rootToNodePath_(root,p,path1);
        rootToNodePath_(root,q,path2);

        Node prev=null;
        int i=path1.size()-1;
        int j=path2.size()-1;
        
        while(i>=0 && j>=0){
          if(path1.get(i).data!=path2.get(j).data) break;
          
          prev=path1.get(i);
          i--;
          j--;
        }

        return prev;
    } 

    static Node LCANode=null;
    public static boolean lowestCommonAncestor_02(Node root, int p, int q) {
        if(root==null) return false;
        
        boolean selfDone=false;
        if(root.data==p || root.data==q){
            selfDone=true;
        }

        boolean leftDone=lowestCommonAncestor_02(root.left,p,q);
        if(LCANode!=null) return true;

        boolean rightDone=lowestCommonAncestor_02(root.right,p,q);
        if(LCANode!=null) return true;
        
        if((selfDone && leftDone) || (selfDone && rightDone) || (leftDone && rightDone))
          LCANode=root;


        return selfDone || leftDone || rightDone; 
    }

    public static void kDown(Node root,int level,Node blockNode){
       if(root==null || root==blockNode) return;

       if(level==0){
         System.out.print(root.data + " ");
         return;  
       }

       kDown(root.left,level-1,blockNode);
       kDown(root.right,level-1,blockNode);

    }

    public static void allNodeKAway(Node root, int target, int K) {
        ArrayList<Node> path=new ArrayList<>();
        rootToNodePath_(root,target,path);

        Node blockNode=null;
        for(int i=0;i<path.size();i++){
            if(K-i<0) break;
            kDown(path.get(i),K-i,blockNode);
            blockNode=path.get(i);
        }
    }
    
    public static int allNodeKAway_02_(Node root, int target, int K) {
         if(root==null) return -1;

         if(root.data == target){
            kDown(root,K,null);
            return 1;
         }

         int leftdistance=allNodeKAway_02_(root.left,target,K);
         if(leftdistance!=-1){
            if(K-leftdistance >= 0) kDown(root,K-leftdistance,root.left);
            return leftdistance+1;
         }
         
         int rightdistance=allNodeKAway_02_(root.right,target,K);
          if(rightdistance!=-1){
            if(K-rightdistance >= 0) kDown(root,K-rightdistance,root.right);
            return rightdistance+1;
        }

        return -1;
    
    }

    public static void kDown(Node root,int level){
        if(root==null) return;
 
        if(level==0){
          System.out.print(root.data + " ");
          return;  
        }
 
        kDown(root.left,level-1);
        kDown(root.right,level-1);
 
     }

    public static int allNodeKAway_03_(Node root, int target, int K) {
        if(root==null) return -1;

        if(root.data == target){
           kDown(root,K,null);
           return 1;
        }

        int leftdistance=allNodeKAway_03_(root.left,target,K);
        if(leftdistance!=-1){
           if(K-leftdistance == 0)  
              System.out.print(root.data + " ");
           else
              kDown(root.right,K-leftdistance-1);
           return leftdistance+1;
        }
        
        int rightdistance=allNodeKAway_03_(root.right,target,K);
        if(rightdistance!=-1){
            if(K-rightdistance == 0)
               System.out.print(root.data + " ");
            else
               kDown(root.left,K-rightdistance-1);
            return rightdistance+1;
         }

       return -1;
   
   }

   public static int diameter_01(Node node){
       if(node==null) return 0;

       int ld=diameter_01(node.left);
       int rd=diameter_01(node.right);

       int lh=height(node.left);
       int rh=height(node.right);

       int myDia = lh + rh + 2;
       return Math.max(Math.max(ld,rd), myDia);
   }

   public static class diaPair{
       int dia=0;
       int hei=0;

       diaPair(int dia,int hei){
           this.dia=dia;
           this.hei=hei;
       }
   }

   public static diaPair diameter_02(Node node){
    if(node==null) return new diaPair(0,-1) ;

    diaPair lr=diameter_02(node.left); // left result
    diaPair rr=diameter_02(node.right); // right result

    diaPair myRes=new diaPair(0,-1);
    myRes.dia = Math.max(Math.max(lr.dia,rr.dia), (lr.hei+rr.hei+2));
    myRes.hei = Math.max(lr.hei,rr.hei)+1;
    
    return myRes;
}

static int diameter=0;
public static int diameter_03(Node node){
    if(node==null) return -1 ;

    int lh = diameter_03(node.left); // left height
    int rh =diameter_03(node.right); // right height
    
    diameter=Math.max(diameter,lh+rh+2);
    return Math.max(lh,rh)+1;
}

   //LevelOrder_series.=========================================================

   public static void levelOrder_00(Node node){
       LinkedList<Node> pQue=new LinkedList<>(); // addLast and removeFirst.
       pQue.addLast(node);

       while(pQue.size()!=0){
           Node rnode=pQue.removeFirst();
           System.out.print(rnode.data+ " ");
           if(rnode.left!=null) pQue.addLast(rnode.left);
           if(rnode.right!=null) pQue.addLast(rnode.right);
      }
   }

   public static void levelOrder_01(Node node){
    LinkedList<Node> pQue=new LinkedList<>(); // addLast and removeFirst.
    LinkedList<Node> cQue=new LinkedList<>(); // addLast and removeFirst.

    pQue.addLast(node);
    int count=0;
    System.out.print("Level: " + count + " -> ");
    
    while(pQue.size()!=0){
        Node rnode=pQue.removeFirst();
        System.out.print(rnode.data+ " ");
        if(rnode.left!=null) cQue.addLast(rnode.left);
        if(rnode.right!=null) cQue.addLast(rnode.right);

        if(pQue.size()==0){
          LinkedList<Node> temp=pQue;
          pQue=cQue;
          cQue=temp;
          count++;      
         System.out.print("\nLevel: " + count + " -> ");
        }
    }
}

public static void levelOrder_02(Node node){
    LinkedList<Node> Que=new LinkedList<>(); // addLast and removeFirst.

    Que.addLast(node);
    Que.addLast(null);
    int count=0;
    System.out.print("Level: " + count + " -> ");
    
    while(Que.size()!=1){
        Node rnode=Que.removeFirst();
        System.out.print(rnode.data+ " ");
        if(rnode.left!=null) Que.addLast(rnode.left);
        if(rnode.right!=null) Que.addLast(rnode.right);

        if(Que.getFirst()==null){
            Que.removeFirst();
            Que.addLast(null);
            count++;      
            System.out.print("\nLevel: " + count + " -> ");
        }
    }
}

public static void levelOrder_03(Node node){    //MAIN
    LinkedList<Node> Que=new LinkedList<>(); // addLast and removeFirst.

    Que.addLast(node);
    int count=0;
    
    while(Que.size()!=0){
        int size=Que.size();
        System.out.print("Level: " + count + " -> ");
        while(size-->0){          
            Node rnode=Que.removeFirst();
            System.out.print(rnode.data+ " ");

            if(rnode.left!=null) Que.addLast(rnode.left);
            if(rnode.right!=null) Que.addLast(rnode.right);
        }
        count++;
        System.out.println();        
    }
}

// views======================================================================================================

public static void leftview(Node node){
    LinkedList<Node> Que=new LinkedList<>(); // addLast and removeFirst.
    Que.addLast(node);
    
    while(Que.size()!=0){
        int size=Que.size();
        System.out.print(Que.getFirst().data+" ");
        while(size-->0){          
            Node rnode=Que.removeFirst();
            if(rnode.left!=null) Que.addLast(rnode.left);
            if(rnode.right!=null) Que.addLast(rnode.right);
        }
    }
}

public static void rightview(Node node){
    LinkedList<Node> Que=new LinkedList<>(); // addLast and removeFirst.
    Que.addLast(node);
    
    while(Que.size()!=0){
        int size=Que.size();
        Node prev=null;
        while(size-->0){          
            Node rnode=Que.removeFirst();
            if(rnode.left!=null) Que.addLast(rnode.left);
            if(rnode.right!=null) Que.addLast(rnode.right);
            prev=rnode;
        }
        System.out.print(prev.data+" ");

    }
}
static int leftmost=0;
static int rightmost=0;

public static void width(Node node,int lev){
    if(node==null) return;

    leftmost=Math.min(leftmost,lev);
    rightmost=Math.max(rightmost,lev);

    width(node.left,lev-1);
    width(node.right,lev+1);
}

public static class pair{
    Node n;
    int level=0;
    public pair(Node n,int level){
        this.level=level;
        this.n=n;
    }
}

public static void verticalorder(Node node){
    width(node,0);
    ArrayList<ArrayList<Integer>> ans=new ArrayList<>();
    int n=rightmost-leftmost+1;
    for(int i=0;i<n;i++){
        ans.add(new ArrayList());
    }

    LinkedList<pair> que=new LinkedList();
    pair p=new pair(node,-leftmost);
    que.addLast(p);

    while(que.size()!=0){
        int size=que.size();
        while(size-->0){
            pair idx=que.removeFirst();
            ans.get(idx.level).add(idx.n.data);
            if(idx.n.left!=null) que.addLast(new pair(idx.n.left,idx.level-1));
            if(idx.n.right!=null) que.addLast(new pair(idx.n.right,idx.level+1));            
        }
    }
    for(ArrayList<Integer> ar:ans){
        System.out.print(ar+" ");
        
    System.out.println();
    }
}

public static void verticalordersum(Node node){
    width(node,0);    
    int n=rightmost-leftmost+1;
    int[] ans=new int[n];

    LinkedList<pair> que=new LinkedList();
    pair p=new pair(node,-leftmost);
    que.addLast(p);

    while(que.size()!=0){
        int size=que.size();
        while(size-->0){
            pair idx=que.removeFirst();
            ans[idx.level]+=(idx.n.data);
            if(idx.n.left!=null) que.addLast(new pair(idx.n.left,idx.level-1));
            if(idx.n.right!=null) que.addLast(new pair(idx.n.right,idx.level+1));            
        }
    }
    for(int ar:ans){
        System.out.print(ar+" ");
        
    }
}

public static void topview(Node node){
    width(node,0);
    int n=rightmost-leftmost+1;
    int[] ans=new int[n];
    for(int i=0;i<n;i++){
        ans[i]=(int)-1e8;
    }

    LinkedList<pair> que=new LinkedList();
    pair p=new pair(node,-leftmost);
    que.addLast(p);

    while(que.size()!=0){
        int size=que.size();
        while(size-->0){
            pair idx=que.removeFirst();
            if(ans[idx.level]==(int)-1e8)
                ans[idx.level]=(idx.n.data);
            if(idx.n.left!=null) que.addLast(new pair(idx.n.left,idx.level-1));
            if(idx.n.right!=null) que.addLast(new pair(idx.n.right,idx.level+1));            
        }
    }
    for(int ar:ans){
        System.out.print(ar+" ");        
        System.out.println();
    }
}

public static void bottomview(Node node){
    width(node,0);
    int n=rightmost-leftmost+1;
    int[] ans=new int[n];

    LinkedList<pair> que=new LinkedList();
    que.addLast(new pair(node,-leftmost));
    while(que.size()!=0){
        int size=que.size();
        while(size-->0){
            pair idx=que.removeFirst();

            ans[idx.level]=idx.n.data;
            if(idx.n.left!=null) que.addLast(new pair(idx.n.left,idx.level-1));
            if(idx.n.right!=null) que.addLast(new pair(idx.n.right,idx.level+1));
        }
    }
    for(int a:ans){
        System.out.print(a+" ");
        System.out.println();
    }
    
}

//question==============================================

public static Node linearize(Node root){
    if(root.left==null&&root.right==null) return root;
    
    if(root.left==null&&root.right!=null){
        root.left=root.right;
        root.right=null;
        return linearize(root.right);
    }
    Node leftLast=linearize(root.left);
    if(root.right!=null){
        Node rightLast=linearize(root.right);
        leftLast.left=root.right;
        root.right=null;
        return rightLast;
    }
    return leftLast;
}


public static void set(Node node){
    linearize(node);
    display(node);
}

public static void views(Node node){
    // leftview(node);
    // rightview(node);
    // verticalOrder(node);
    // verticalordersum(node);
    // bottomview(node);
    // topview(node);
}

   public static void levelOrder(Node node){
    // levelOrder_00(node);
    // levelOrder_01(node);
    // levelOrder_02(node);
    levelOrder_03(node);

   }


   public static void set1(Node node){
    //   rootToNodePath(node,100);
    
    //   lowestCommonAncestor_02(node,200,30);
    //   System.out.println("LCA: " + (LCANode!=null?LCANode.data:"-1"));
    // allNodeKAway(node,20,1);


    System.out.println(diameter_01(node));
    System.out.println(diameter_02(node).dia);
    diameter_03(node);
    System.out.println(diameter);
   }

   public static void solve(){
    //    int[] arr={10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
    int[] arr=   {11,6,4 ,-1, 5 ,-1, -1, 8, -1, 10, -1, -1, 19, 17, -1, -1, 43, 31, -1, -1, 49, -1, -1};
    //    int[] arr={10,20};
       Node root=constructTree(arr);
       display(root);
    //    set1(root);
    //    levelOrder(root);
    //    views(root);
    // set(root);
   }
}