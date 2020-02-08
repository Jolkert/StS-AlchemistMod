package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import java.util.ArrayList;

import static theAlchemist.AlchemistMod.makeCardPath;

public class NewBeginnings extends CustomCard
{
	public static final String ID = AlchemistMod.makeID(NewBeginnings.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("NewBeginnings.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 2;
	
	
	public NewBeginnings()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
		this.exhaust = true;
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		int count = 0;
		ArrayList<AbstractCard> toExhaust = new ArrayList<AbstractCard>();
		for(AbstractCard card : player.hand.group)
			if(card.type == CardType.CURSE || card.type == CardType.STATUS)
			{
				toExhaust.add(card);
				count++;
			}
		
		if(count > 0)
			for(AbstractCard card : toExhaust)
			{
				AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, player.hand));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractElement.returnRandomElement(0)));
			}
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			this.exhaust = false;
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
