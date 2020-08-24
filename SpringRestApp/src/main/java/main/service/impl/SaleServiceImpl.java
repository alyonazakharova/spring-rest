package main.service.impl;

import main.entity.Sale;
import main.exception.SaleNotFoundException;
import main.repository.SaleRepository;
import main.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> listSales() {
        return (List<Sale>) saleRepository.findAll();
    }

    @Override
    public Sale find(Integer id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isPresent()) {
            return sale.get();
        } else {
            throw new SaleNotFoundException("Sale not found");
        }
    }

    @Override
    public Sale add(Sale sale) {
        return saleRepository.save(sale);
    }

    //как должно все удаляться???
    @Override
    public void delete(Integer id) {
        try {
            saleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new SaleNotFoundException("Sale not found");
        }
    }

    @Override
    public Sale update(Integer id, Sale sale) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sale s = optionalSale.get();
            s.setGood(sale.getGood());
            s.setGoodCount(sale.getGoodCount());
            s.setCreateDate(sale.getCreateDate());
            return saleRepository.saveAndFlush(s);
        } else {
            throw new SaleNotFoundException("Sale not found");
        }
    }
}
