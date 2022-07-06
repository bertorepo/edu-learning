package com.hubert.freebies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubert.constants.Constants;

@Service
public class FreebiesServiceImpl implements Ifreebies {

    private FreebisRepository freebiesRepository;

    @Autowired
    public FreebiesServiceImpl(FreebisRepository freebiesRepository) {
        this.freebiesRepository = freebiesRepository;
    }

    @Override
    public Freebies saveFreebie(FreebiesDao freebiesDao) {
        Freebies freebie = null;
        if (freebiesDao != null) {
            freebie = new Freebies();
            freebie.setFreebieName(freebiesDao.getFreebieName());
            freebie.setFreebieSize(freebiesDao.getFreebieSize());
            freebie.setFreebieDescription(freebiesDao.getFreebieDescription());
            freebie.setFreebieLink(freebiesDao.getFreebieLink());
            freebie.setEnabled(Constants.IS_ENABLED);
            freebiesRepository.save(freebie);
        }
        return freebie;
    }

}
