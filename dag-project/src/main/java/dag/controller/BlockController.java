package dag.controller;

import dag.pojo.Block;
import dag.pojo.CommonResult;
import dag.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class BlockController {

    @Autowired
    private BlockService blockService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save() {
        return "save";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam(value="data", required = true) String data, Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String hash = blockService.addBlock(data).get();
        model.addAttribute("data", data);
        model.addAttribute("hash", hash);
        return "save";
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read() {
        return "read";
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public String read(@RequestParam(value="hash", required = false) String hash, Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        if (hash != null) {
            String data = blockService.getBlockByHash(hash).get();
            model.addAttribute("hash", hash);
            model.addAttribute("data", data);
        }
        return "read";
    }

    @ResponseBody
    @RequestMapping(value = "/getBlockList", method = RequestMethod.GET)
    public List<Block> getBlockList() {
         System.out.println(blockService.getBlockList());
        return blockService.getBlockList();
    }

    @ResponseBody
    @RequestMapping(value="/getBlockById/{id}", method = RequestMethod.GET)
    public Block getBlockById(@PathVariable(name = "id") int id) {
        Block block = blockService.getBlockById(id);
        System.out.println(block.getData());
        return block;
    }

    @ResponseBody
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    public CommonResult saveData(@RequestParam(value = "data", required = true) String data) {
        CommonResult result = new CommonResult();
        String res;
        try {
            res = blockService.addBlock(data).get();
            if (res != null) {
                result.setData(res);
                result.setSuccess();
            } else {
                result.setFailure();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setException();
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/readData", method = RequestMethod.POST)
    public CommonResult readData(@RequestParam(name = "hash", required = true) String hash) {
        CommonResult result = new CommonResult();
        try {
            String data = blockService.getBlockByHash(hash).get();
            if (data != null) {
                result.setSuccess();
                result.setData(data);
            } else {
                result.setFailure();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setException();
            return result;
        }
    }
}
