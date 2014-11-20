package com.andy0805.crawler.frontier;

import org.archive.crawler.frontier.QueueAssignmentPolicy;
import org.archive.modules.CrawlURI;

public class MyQueueAssignmentPolicy extends QueueAssignmentPolicy{

	/**
	 * 
	 */
	private static final long serialVersionUID = -497149797994643767L;

	@Override
	public String getClassKey(CrawlURI cauri) {
		 // TODO Auto-generated method stub
        String uri = cauri.getURI().toString();         
        long hash = ELFHash(uri);//利用ELFHash算法为uri分配Key值         
        String a = Long.toString(hash % 50);//取模50，对应50个线程         
        return a;
	}
	
	public long ELFHash(String str)      
    {         
        long hash = 0;         
        long x   = 0;         
        for(int i = 0; i < str.length(); i++)         
        {            
            hash = (hash << 4) + str.charAt(i);//将字符中的每个元素依次按前四位与上            
            if((x = hash & 0xF0000000L) != 0)//个元素的低四位想与           
            {               
                hash ^= (x >> 24);//长整的高四位大于零，折回再与长整后四位异或              
                hash &= ~x;            
            }         
        }         
        return (hash & 0x7FFFFFFF);      
    }

}
