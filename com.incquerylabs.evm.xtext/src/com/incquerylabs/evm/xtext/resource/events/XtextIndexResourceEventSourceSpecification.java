package com.incquerylabs.evm.xtext.resource.events;

import org.eclipse.incquery.runtime.evm.api.RuleInstance;
import org.eclipse.incquery.runtime.evm.api.event.AbstractRuleInstanceBuilder;
import org.eclipse.incquery.runtime.evm.api.event.EventFilter;
import org.eclipse.incquery.runtime.evm.api.event.EventRealm;
import org.eclipse.incquery.runtime.evm.api.event.EventSourceSpecification;

import com.google.common.base.Preconditions;
import com.incquerylabs.evm.xtext.XtextEventRealm;
import com.incquerylabs.evm.xtext.XtextIndexEventHandler;
import com.incquerylabs.evm.xtext.resource.XtextIndexedResource;

public class XtextIndexResourceEventSourceSpecification implements EventSourceSpecification<XtextIndexedResource> {

	private final EventFilter<XtextIndexedResource> eventFilter = new EventFilter<XtextIndexedResource>() {
		
		@Override
		public boolean isProcessable(XtextIndexedResource eventAtom) {
			return true;
		}
	};
	
	@Override
	public EventFilter<XtextIndexedResource> createEmptyFilter() {
		return eventFilter;
	}

	@Override
	public AbstractRuleInstanceBuilder<XtextIndexedResource> getRuleInstanceBuilder(final EventRealm realm) {
		Preconditions.checkArgument(realm instanceof XtextEventRealm, "Event realm must be an XtextEventRealm");
		return new AbstractRuleInstanceBuilder<XtextIndexedResource>() {

			@Override
			public void prepareRuleInstance(RuleInstance<XtextIndexedResource> ruleInstance,
					EventFilter<? super XtextIndexedResource> filter) {
				XtextIndexedResourceEventSource source = new XtextIndexedResourceEventSource(
						XtextIndexResourceEventSourceSpecification.this, (XtextEventRealm) realm);
				source.prepareSource();
				XtextIndexEventHandler<XtextIndexedResource, XtextIndexedResourceEventSource> handler = new XtextIndexEventHandler<>(
						source, filter, ruleInstance);
				handler.prepareEventHandler();
			}
		};
	}

}
