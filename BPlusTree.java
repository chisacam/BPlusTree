import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BPlusTree {
    private ManageNode mngnode = new ManageNode();
    private WordNode newword;
    ArrayList<DocNode> searchlist = new ArrayList();
    int i = 0;

    public BPlusTree() {
    }

    public IndexNode insertNode(IndexNode root, String word, String filename) {
        if (root.getHasDataLink()) {
            if (word.compareTo(root.getWords(0)) < 0) {
                this.newword = new WordNode(word);
                this.newword.insertDocNode(filename);
                root.dleftlink.setWorddata(this.newword, filename);
                if (this.mngnode.isDataFull(root.dleftlink)) {
                    root = this.mngnode.splitDataNode(root, root.dleftlink);
                }
            } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                this.newword = new WordNode(word);
                this.newword.insertDocNode(filename);
                root.drightlink.setWorddata(this.newword, filename);
                if (this.mngnode.isDataFull(root.drightlink)) {
                    root = this.mngnode.splitDataNode(root, root.drightlink);
                }
            } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
                this.newword = new WordNode(word);
                this.newword.insertDocNode(filename);
                root.dmidlink.setWorddata(this.newword, filename);
                if (this.mngnode.isDataFull(root.dmidlink)) {
                    root = this.mngnode.splitDataNode(root, root.dmidlink);
                }
            } else if (word.compareTo(root.getWords(1)) >= 0) {
                this.newword = new WordNode(word);
                this.newword.insertDocNode(filename);
                root.drightlink.setWorddata(this.newword, filename);
                if (this.mngnode.isDataFull(root.drightlink)) {
                    root = this.mngnode.splitDataNode(root, root.drightlink);
                }
            }

            if (this.mngnode.isIndexNodeFull(root)) {
                root = this.mngnode.splitIndexNode(root);
            }

            return root;
        } else {
            if (word.compareTo(root.getWords(0)) < 0) {
                this.insertNode(root.ileftlink, word, filename);
            } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                this.insertNode(root.irightlink, word, filename);
            } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
                this.insertNode(root.imidlink, word, filename);
            } else if (word.compareTo(root.getWords(1)) >= 0) {
                this.insertNode(root.irightlink, word, filename);
            }

            if (this.mngnode.isIndexNodeFull(root)) {
                root = this.mngnode.splitIndexNode(root);
            }

            return root;
        }
    }

    public void treeToFile(IndexNode root) {
        try {
            FileOutputStream fos = new FileOutputStream("bptree.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(root);
            oos.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public IndexNode fileToTree() {
        try {
            FileInputStream fis = new FileInputStream("bptree.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            IndexNode root = (IndexNode)ois.readObject();
            return root;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public void searchLeaf(IndexNode root, String word) {
        if (root.getHasDataLink()) {
            int count;
            DocNode temp;
            if (word.compareTo(root.getWords(0)) < 0) {
                count = root.dleftlink.getDataCount();
                switch(count) {
                case 1:
                    if (root.dleftlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.dleftlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dleftlink.getWorddata(0).getLength()));
                        }

                        return;
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                        break;
                    }
                case 2:
                    if (root.dleftlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.dleftlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dleftlink.getWorddata(0).getLength()));
                        }
                    } else if (root.dleftlink.getWorddata(1).getWord().compareTo(word) == 0) {
                        for(temp = root.dleftlink.getWorddata(1).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dleftlink.getWorddata(1).getLength()));
                        }
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                    }
                }
            } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
                count = root.drightlink.getDataCount();
                switch(count) {
                case 1:
                    if (root.drightlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(0).getLength()));
                        }

                        return;
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                        break;
                    }
                case 2:
                    if (root.drightlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(0).getLength()));
                        }
                    } else if (root.drightlink.getWorddata(1).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(1).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(1).getLength()));
                        }
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                    }
                }
            } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
                count = root.dmidlink.getDataCount();
                switch(count) {
                case 1:
                    if (root.dmidlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.dmidlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dmidlink.getWorddata(0).getLength()));
                        }

                        return;
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                        break;
                    }
                case 2:
                    if (root.dmidlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.dmidlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dmidlink.getWorddata(0).getLength()));
                        }
                    } else if (root.dmidlink.getWorddata(1).getWord().compareTo(word) == 0) {
                        for(temp = root.dmidlink.getWorddata(1).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.dmidlink.getWorddata(1).getLength()));
                        }
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                    }
                }
            } else if (word.compareTo(root.getWords(1)) >= 0) {
                count = root.drightlink.getDataCount();
                switch(count) {
                case 1:
                    if (root.drightlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(0).getLength()));
                        }

                        return;
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                        break;
                    }
                case 2:
                    if (root.drightlink.getWorddata(0).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(0).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(0).getLength()));
                        }
                    } else if (root.drightlink.getWorddata(1).getWord().compareTo(word) == 0) {
                        for(temp = root.drightlink.getWorddata(1).getHead(); temp != null; temp = temp.next) {
                            this.searchlist.add(new DocNode(temp.getDocname(), temp.getDocfreq() / (double)root.drightlink.getWorddata(1).getLength()));
                        }
                    } else {
                        System.out.println(word + " 은/는 없는 데이터입니다.");
                    }
                }
            }
        } else if (word.compareTo(root.getWords(0)) < 0) {
            this.searchLeaf(root.ileftlink, word);
        } else if (word.compareTo(root.getWords(0)) >= 0 && root.getWords(1) == null) {
            this.searchLeaf(root.irightlink, word);
        } else if (word.compareTo(root.getWords(0)) >= 0 && word.compareTo(root.getWords(1)) < 0) {
            this.searchLeaf(root.imidlink, word);
        } else if (word.compareTo(root.getWords(1)) >= 0) {
            this.searchLeaf(root.irightlink, word);
        }

    }

    public void printArrayList() {
        Collections.sort(this.searchlist, new 1(this));
        Iterator var1 = this.searchlist.iterator();

        while(var1.hasNext()) {
            DocNode n = (DocNode)var1.next();
            PrintStream var10000 = System.out;
            String var10001 = n.getDocname();
            var10000.println(var10001 + " " + n.getDocfreq());
        }

        this.searchlist.clear();
    }

    public void printIndexNode(IndexNode root) {
        System.out.println(this.i);
        System.out.println(root.getWords(0));
        if (root.getWords(1) != null) {
            System.out.println(root.getWords(1));
        }

        if (root.getWords(2) != null) {
            System.out.println(root.getWords(2));
        }

        ++this.i;
        if (root.ileftlink != null) {
            this.printIndexNode(root.ileftlink);
        }

        if (root.imidlink != null) {
            this.printIndexNode(root.imidlink);
        }

        if (root.irightlink != null) {
            this.printIndexNode(root.irightlink);
        }

        if (root.itemplink != null) {
            this.printIndexNode(root.itemplink);
        }

        if (root.dleftlink != null) {
            System.out.println(root.dleftlink.getWorddata(0).getWord());
            if (root.dleftlink.getWorddata(1) != null) {
                System.out.println(root.dleftlink.getWorddata(1).getWord());
            }
        }

        if (root.dmidlink != null) {
            System.out.println(root.dmidlink.getWorddata(0).getWord());
            if (root.dmidlink.getWorddata(1) != null) {
                System.out.println(root.dmidlink.getWorddata(1).getWord());
            }
        }

        if (root.dleftlink != null) {
            System.out.println(root.drightlink.getWorddata(0).getWord());
            if (root.drightlink.getWorddata(1) != null) {
                System.out.println(root.drightlink.getWorddata(1).getWord());
            }
        }

    }

    public void printDataNode(IndexNode root) {
        while(root.ileftlink != null) {
            root = root.ileftlink;
        }

        DataNode temp = root.dleftlink;

        for(this.i = 0; temp != null; temp = temp.next) {
            System.out.println(this.i);
            System.out.println(temp.getWorddata(0).getWord());
            if (temp.getWorddata(1) != null) {
                System.out.println(temp.getWorddata(1).getWord());
            }

            ++this.i;
        }

    }
}
