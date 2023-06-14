package com.hospital.hospital.service;

import com.hospital.hospital.db.mysql.BlockDAO;
import com.hospital.hospital.model.Block;

import java.util.List;

public class BlockService implements Service<Block> {
    private final BlockDAO blockDao;

    public BlockService(BlockDAO blockDao) {
        this.blockDao = blockDao;
    }

    @Override
    public Block findById(int id) {
        // Implement the findById method with a single parameter in the Service interface
        // This method can throw an exception or provide a default implementation if not needed for BlockService
        throw new UnsupportedOperationException("findById(int id) is not supported in BlockService.");
    }

    public Block findById(int floor, int code) {
        // Implement the findById method specific to BlockService with floor and code parameters
        return blockDao.findByFloorAndCode(floor, code);
    }

    @Override
    public List<Block> findAll() {
        return blockDao.findAll();
    }

    @Override
    public void save(Block entity) {
        blockDao.save(entity);
    }

    @Override
    public void update(Block entity) {
        blockDao.update(entity);
    }

    @Override
    public void delete(Block entity) {
        blockDao.delete(entity);
    }
}
