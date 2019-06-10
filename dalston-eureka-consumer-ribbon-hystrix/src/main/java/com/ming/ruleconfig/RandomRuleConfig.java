package com.ming.ruleconfig;

import com.ming.rule.MingRule;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;

public class RandomRuleConfig {
	
	@Bean
	public IRule mingRule() {
		return new MingRule();
	}

}
