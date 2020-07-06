import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BPlusTree {
    private ManageNode mngnode;
    private WordNode newword;
    ArrayList<DocNode> searchlist = new ArrayList<>();
    int i = 0;
    public BPlusTree(){
        mngnode = new ManageNode();
    }
    public IndexNode insertNode(IndexNode root, String word, String filename){
        if(root.getHasDataLink()) {
            if (word.compareTo(root.getWords(0)) < 0) {
                newword = new WordNode(word);
                newword.insertDocNode(filename);
                root.dleftlink.setWorddata(newword, filename);
                if (mngnode.isDataFull(root.dleftlink)) {
                    root = mngnode.splitDataNode(root, root.dleftlink);
                }
            }else if(word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                newword = new WordNode(word);
                newword.insertDocNode(filename);
                root.drightlink.setWorddata(newword, filename);
                if(mngnode.isDataFull(root.drightlink)){
                    root = mngnode.splitDataNode(root, root.drightlink);
                }
            }else if(word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0){
                newword = new WordNode(word);
                newword.insertDocNode(filename);
                root.dmidlink.setWorddata(newword, filename);
                if(mngnode.isDataFull(root.dmidlink)){
                    root = mngnode.splitDataNode(root, root.dmidlink);
                }
            }else if(word.compareTo(root.getWords(1)) >= 0){
                newword = new WordNode(word);
                newword.insertDocNode(filename);
                root.drightlink.setWorddata(newword, filename);
                if(mngnode.isDataFull(root.drightlink)){
                    root = mngnode.splitDataNode(root, root.drightlink);
                }
            }
            if(mngnode.isIndexNodeFull(root)){
                root = mngnode.splitIndexNode(root);
            }
            return root;
        } else {
            if (word.compareTo(root.getWords(0)) < 0) {
                insertNode(root.ileftlink, word, filename);
            } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                insertNode(root.irightlink, word, filename);
            } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
                insertNode(root.imidlink, word, filename);
            } else if (word.compareTo(root.getWords(1)) >= 0) {
                insertNode(root.irightlink, word, filename);
            }
            if(mngnode.isIndexNodeFull(root)){
                root = mngnode.splitIndexNode(root);
            }
        }
        return root;
    }
    // 트리를 물리디스크에 캐싱하는 메소드
    public void treeToFile(IndexNode root){
        try{
            FileOutputStream fos = new FileOutputStream("bptree.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(root);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //캐시를 트리로 재구성하는 메소드
    public IndexNode fileToTree(){
        try {
            FileInputStream fis = new FileInputStream("bptree.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            IndexNode root = (IndexNode)ois.readObject();
            return root;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // 데이터 노드를 찾아서 문서노드들을 ArrayList에 저장하는 메소드
    public void searchLeaf(IndexNode root, String word){
        if(root.getHasDataLink()){
            if (word.compareTo(root.getWords(0)) < 0) {
                if(root.dleftlink.getWorddata(0).getWord().compareTo(word) == 0){
                    DocNode temp = root.dleftlink.getWorddata(0).getHead();
                    while(temp != null){
                        searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.dleftlink.getWorddata(0).getLength()));
                        temp = temp.next;
                    }
                }else if(root.dleftlink.getWorddata(1) != null){
                    if(root.dleftlink.getWorddata(1).getWord().compareTo(word) == 0){
                        DocNode temp = root.dleftlink.getWorddata(1).getHead();
                        while(temp != null){
                            searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.dleftlink.getWorddata(1).getLength()));
                            temp = temp.next;
                        }
                    }
                }else{
                    System.out.println(word + " 를 찾지 못했습니다.");
                }
            }else if(word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                if(root.drightlink.getWorddata(0).getWord().compareTo(word) == 0){
                    DocNode temp = root.drightlink.getWorddata(0).getHead();
                    while(temp != null){
                        searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.drightlink.getWorddata(0).getLength()));
                        temp = temp.next;
                    }
                }else if(root.drightlink.getWorddata(1) != null){
                    if(root.drightlink.getWorddata(1).getWord().compareTo(word) == 0){
                        DocNode temp = root.drightlink.getWorddata(1).getHead();
                        while(temp != null){
                            searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.drightlink.getWorddata(1).getLength()));
                            temp = temp.next;
                        }
                    }
                }else{
                    System.out.println(word + " 를 찾지 못했습니다.");
                }
            }else if(word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0){
                if(root.dmidlink.getWorddata(0).getWord().compareTo(word) == 0){
                    DocNode temp = root.dmidlink.getWorddata(0).getHead();
                    while(temp != null){
                        searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.dmidlink.getWorddata(0).getLength()));
                        temp = temp.next;
                    }
                }else if(root.dmidlink.getWorddata(1) != null){
                    if(root.dmidlink.getWorddata(1).getWord().compareTo(word) == 0){
                        DocNode temp = root.dmidlink.getWorddata(1).getHead();
                        while(temp != null){
                            searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.dmidlink.getWorddata(1).getLength()));
                            temp = temp.next;
                        }
                    }
                }else{
                    System.out.println(word + " 를 찾지 못했습니다.");
                }
            }else if(word.compareTo(root.getWords(1)) >= 0){
                if(root.drightlink.getWorddata(0).getWord().compareTo(word) == 0){
                    DocNode temp = root.drightlink.getWorddata(0).getHead();
                    while(temp != null){
                        searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.drightlink.getWorddata(0).getLength()));
                        temp = temp.next;
                    }
                }else if(root.drightlink.getWorddata(1) != null){
                    if(root.drightlink.getWorddata(1).getWord().compareTo(word) == 0){
                        DocNode temp = root.drightlink.getWorddata(1).getHead();
                        while(temp != null){
                            searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / root.drightlink.getWorddata(1).getLength()));
                            temp = temp.next;
                        }
                    }
                }else{
                    System.out.println(word + "를 찾지 못했습니다.");
                }
            }
        }
        else {
            if (word.compareTo(root.getWords(0)) < 0) {
                searchLeaf(root.ileftlink, word);
            } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                searchLeaf(root.irightlink, word);
            } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
                searchLeaf(root.imidlink, word);
            } else if (word.compareTo(root.getWords(1)) >= 0) {
                searchLeaf(root.irightlink, word);
            }
        }
    }
    // ArrayList를 랭킹 순으로 정렬후 출력하는 메소드
    public void printArrayList(){
        Collections.sort(searchlist, new Comparator<DocNode>() {
            @Override
            public int compare(DocNode n1, DocNode n2) {
                if (n1.getDocfreq() < n2.getDocfreq()) {
                    return 1;
                } else if (n1.getDocfreq() > n2.getDocfreq()) {
                    return -1;
                }
                return 0;
            }
        });
        for (DocNode n : searchlist) {
            System.out.println(n.getDocname() + " " + n.getDocfreq());
        }
        searchlist.clear();
    }
    // 인덱스노드 정상저장 확인용 메소드
    public void printIndexNode(IndexNode root){
        System.out.println(i);
        System.out.println(root.getWords(0));
        if(root.getWords(1) != null){
            System.out.println(root.getWords(1));
        }if(root.getWords(2) != null){
            System.out.println(root.getWords(2));
        }i++;
        if(root.ileftlink != null) {
            printIndexNode(root.ileftlink);
        }if(root.imidlink != null) {
            printIndexNode(root.imidlink);
        }if(root.irightlink != null) {
            printIndexNode(root.irightlink);
        }if(root.itemplink != null){
            printIndexNode(root.itemplink);
        }if(root.dleftlink != null){
            System.out.println(root.dleftlink.getWorddata(0).getWord());
            if(root.dleftlink.getWorddata(1) != null){
                System.out.println(root.dleftlink.getWorddata(1).getWord());
            }
        }if(root.dmidlink != null){
            System.out.println(root.dmidlink.getWorddata(0).getWord());
            if(root.dmidlink.getWorddata(1) != null){
                System.out.println(root.dmidlink.getWorddata(1).getWord());
            }
        }if(root.dleftlink != null){
            System.out.println(root.drightlink.getWorddata(0).getWord());
            if(root.drightlink.getWorddata(1) != null){
                System.out.println(root.drightlink.getWorddata(1).getWord());
            }
        }
    }
    //데이터노드간 링크 정상연결 확인 및 정상저장 확인 메소드
    public void printDataNode(IndexNode root){
        while(root.ileftlink != null){
            root = root.ileftlink;
        }
        DataNode temp = root.dleftlink;
        i = 0;
        while(temp != null){
            System.out.println(i);
            System.out.println(temp.getWorddata(0).getWord());
            if(temp.getWorddata(1) != null){
                System.out.println(temp.getWorddata(1).getWord());
            }
            i++;
            temp = temp.next;
        }
    }
}