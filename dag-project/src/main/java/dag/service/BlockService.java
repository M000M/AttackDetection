package dag.service;

import dag.pojo.Block;

import java.util.List;
import java.util.concurrent.Future;

public interface BlockService {

    List<Block> getBlockList();

    Block getBlockById(int id);

    Future<String> addBlock(String data);

    Future<String> getBlockByHash(String hash);
}
