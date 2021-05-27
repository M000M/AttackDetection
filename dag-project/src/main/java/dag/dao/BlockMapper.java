package dag.dao;

import dag.pojo.Block;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockMapper {

    List<Block> getBlockList();

    Block getBlockById(int id);

    int addBlock(Block block);

    Block getBlockByHash(String hash);

}
