public class ManageNode {
    //데이터노드를 찾는 메소드
    public DataNode findDataNode(IndexNode root){
        DataNode foundnode = new DataNode();
        return foundnode;
    }
    //데이터노드가 가득찼는지 확인하는 메소드
    public boolean isDataFull(DataNode node){
        if(node.getDataCount() == 3) {
            return true;
        }else{
            return false;
        }
    }/*
    //노드 인덱스 링크가 가득 찼는지 확인하는 메소드
    public boolean isILinkFull(IndexNode node){
        if(node.getCountilink() == 3){
            return true;
        }else{
            return false;
        }
    }
    //노드 데이터 링크가 가득 찼는지 확인하는 메소드
    public boolean isDLinkFull(IndexNode node){
        if(node.getCountdlink() == 3){
            return true;
        }else{
            return false;
        }
    }*/
    //인덱스노드 키가 가득찼는지 확인하는 메소드
    public boolean isIndexNodeFull(IndexNode node){
        if(node.getCountwords() == 3){
            return true;
        }else{
            return false;
        }
    }
    //데이터노드가 가득 찼을때 분할하는 메소드
    public IndexNode splitDataNode(IndexNode inode, DataNode dnode){
        DataNode newnode = new DataNode();
        newnode.setWorddata(dnode.getWorddata(1));
        newnode.setWorddata(dnode.getWorddata(2));
        dnode.setWorddatanull();
        dnode.setNextDataLink(dnode, newnode);
        dnode.setDataCount(1);
        inode.setWords(newnode.getWorddata(0).getWord());
        if(inode.dleftlink == null
                && inode.dmidlink == null
                && inode.drightlink == null){
            inode.dleftlink = dnode;
            inode.drightlink = newnode;
        }else if(inode.dleftlink != null
                && inode.dmidlink == null
                && inode.drightlink != null) {
            if (dnode == inode.dleftlink) {
                inode.dmidlink = newnode;
            } else if (dnode == inode.drightlink) {
                inode.dmidlink = dnode;
                inode.drightlink = newnode;
            }
        }else if(inode.dleftlink != null
                && inode.dmidlink != null
                && inode.drightlink != null){
            if(dnode == inode.dleftlink){
                inode.dtemplink = inode.drightlink;
                inode.drightlink = inode.dmidlink;
                inode.dmidlink = newnode;
            }else if(dnode == inode.dmidlink){
                inode.dtemplink = inode.drightlink;
                inode.drightlink = newnode;
            }else if(dnode == inode.drightlink){
                inode.dtemplink = newnode;
            }
        }
        inode.setHasDataLink(true);
        return inode;
    }
    //부모노드가 없는 인덱스노드가 가득 찼을때 분할하는 메소드


    //인덱스노드 링크만 가지는 인덱스노드를 분할하는 메소드
    public IndexNode splitIndexNode(IndexNode node){
        if(node.getHasParent() != null){
            if(node.getHasDataLink()){
                IndexNode newnode = new IndexNode();
                node.parent.setWords(node.getWords(1));
                newnode.setWords(node.getWords(2));
                node.setWordnull();
                node.setCountwords(1);
                newnode.dleftlink = node.drightlink;
                newnode.drightlink = node.dtemplink;
                node.drightlink = node.dmidlink;
                node.dmidlink = null;
                node.dtemplink = null;
                newnode.parent = node.parent;
                if(node.parent.ileftlink != null
                        && node.parent.imidlink == null
                        && node.parent.irightlink != null) {
                    if (node == node.parent.ileftlink) {
                        node.parent.imidlink = newnode;
                    } else if (node == node.parent.irightlink) {
                        node.parent.imidlink = node;
                        node.parent.irightlink = newnode;
                    }
                }else if(node.parent.ileftlink != null
                        && node.parent.imidlink != null
                        && node.parent.irightlink != null){
                    if(node == node.parent.ileftlink){
                        node.parent.itemplink = node.parent.irightlink;
                        node.parent.irightlink = node.parent.imidlink;
                        node.parent.imidlink = newnode;
                    }else if(node == node.parent.imidlink){
                        node.parent.itemplink = node.parent.irightlink;
                        node.parent.irightlink = newnode;
                    }else if(node == node.parent.irightlink){
                        node.parent.itemplink = newnode;
                    }
                }
                newnode.setHasDataLink(true);
                return node;
            }else{
                IndexNode newnode = new IndexNode();
                node.parent.setWords(node.getWords(1));
                newnode.setWords(node.getWords(2));
                node.setWordnull();
                node.setCountwords(1);
                newnode.ileftlink = node.irightlink;
                newnode.irightlink = node.itemplink;
                node.irightlink = node.imidlink;
                node.imidlink = null;
                node.itemplink = null;
                newnode.parent = node.parent;
                newnode.ileftlink.parent = newnode;
                newnode.irightlink.parent = newnode;
                if(node.parent.ileftlink != null
                        && node.parent.imidlink == null
                        && node.parent.irightlink != null) {
                    if (node == node.parent.ileftlink) {
                        node.parent.imidlink = newnode;
                    } else if (node == node.parent.irightlink) {
                        node.parent.imidlink = node;
                        node.parent.irightlink = newnode;
                    }
                }else if(node.parent.ileftlink != null
                        && node.parent.imidlink != null
                        && node.parent.irightlink != null){
                    if(node == node.parent.ileftlink){
                        node.parent.itemplink = node.parent.irightlink;
                        node.parent.irightlink = node.parent.imidlink;
                        node.parent.imidlink = newnode;
                    }else if(node == node.parent.imidlink){
                        node.parent.itemplink = node.parent.irightlink;
                        node.parent.irightlink = newnode;
                    }else if(node == node.parent.irightlink){
                        node.parent.itemplink = newnode;
                    }
                }
                return node;
            }
        }else{
            if(node.getHasDataLink()) {
                IndexNode newnode = new IndexNode();
                IndexNode rootnode = new IndexNode();
                rootnode.setWords(node.getWords(1));
                newnode.setWords(node.getWords(2));
                node.setWordnull();
                node.setCountwords(1);
                newnode.dleftlink = node.drightlink;
                newnode.drightlink = node.dtemplink;
                node.drightlink = node.dmidlink;
                node.dmidlink = null;
                node.dtemplink = null;
                rootnode.ileftlink = node;
                rootnode.irightlink = newnode;
                node.parent = rootnode;
                newnode.parent = rootnode;
                node.setHasDataLink(true);
                rootnode.irightlink.setHasDataLink(true);
                return rootnode;
            }else{
                IndexNode newnode = new IndexNode();
                IndexNode rootnode = new IndexNode();
                rootnode.setWords(node.getWords(1));
                newnode.setWords(node.getWords(2));
                node.setWordnull();
                node.setCountwords(1);
                newnode.ileftlink = node.irightlink;
                newnode.irightlink = node.itemplink;
                node.irightlink = node.imidlink;
                node.imidlink = null;
                node.itemplink = null;
                rootnode.ileftlink = node;
                rootnode.irightlink = newnode;
                node.parent = rootnode;
                newnode.parent = rootnode;
                newnode.ileftlink.parent = newnode;
                newnode.irightlink.parent = newnode;
                node.setHasDataLink(false);
                newnode.setHasDataLink(false);
                return rootnode;
            }
        }
    }
}
